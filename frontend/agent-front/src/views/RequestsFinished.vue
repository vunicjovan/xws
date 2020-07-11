<template>
	<div id="component">
		<flash-message class="myFlash"></flash-message>
		<div class="md-layout md-gutter md-alignment-center">
			<md-dialog :md-active.sync="active">
				<md-dialog-title>Report</md-dialog-title>
				<md-dialog-content>
					<form>
						<md-field :class="{ 'md-invalid': $v.form.kilometersTraveled.$error }">
							<label>Kilometers Traveled</label>
							<md-input v-model="form.kilometersTraveled" type="number"></md-input>
							<span class="md-error" v-if="!$v.form.kilometersTraveled.required">Kilometers traveled is required</span>
							<span class="md-error" v-else-if="!$v.form.kilometersTraveled.integer">Kilometers traveled must be integer number</span>
						</md-field>
						<md-field :class="{ 'md-invalid': $v.form.content.$error }">
							<label>Free Notes</label>
							<md-textarea v-model="form.content"></md-textarea>
							<span class="md-error" v-if="!$v.form.content.required">Content is required</span>
							<span class="md-error" v-else-if="!$v.form.content.sqli">Content is not properly formed</span>
						</md-field>
					</form>
				</md-dialog-content>
				<md-dialog-content>
					<md-button class="md-primary" @click="active = false">Cancel</md-button>
					<md-button class="md-primary" @click.prevent="validateForm()">Send</md-button>
				</md-dialog-content>
			</md-dialog>
			<md-table v-model="requests" md-card class="md-layout-item md-size-50 md-small-size-100">
				<md-table-toolbar>
					<h1 class="md-title md-toolbar-section-start">Finished Requests</h1>
				</md-table-toolbar>

				<md-table-row v-if="requests.length !== 0" slot="md-table-row" slot-scope="{ item }">
					<md-table-cell md-label="Vehicle">{{ item.brand }} {{ item.model }}</md-table-cell>
					<md-table-cell md-label="Interval">{{ item.stringStartDate }} - {{ item.stringEndDate }}</md-table-cell>
					<md-table-cell md-label="Report">
						<md-button @click="prepareForReport(item)" class="md-icon-button">
							<md-icon class="fas fa-pen" />
						</md-button>
					</md-table-cell>
				</md-table-row>
			</md-table>
		</div>
	</div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { validationMixin } from "vuelidate";
import { required, integer, decimal, helpers } from "vuelidate/lib/validators";

const sqli = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\s?]+)$/);

export default {
	name: "RequestsFinished",
	mixins: [validationMixin],
	data() {
		return {
			active: false,
			form: {
				kilometersTraveled: undefined,
				content: undefined,
				requestID: undefined,
				advertisementID: undefined,
			},
		};
	},
	mounted() {
		this.$store.dispatch("getFinishedRentingRequests");
	},
	computed: {
		...mapGetters(["getFinishedRequests"]),
		requests: {
			get() {
				return this.getFinishedRequests;
			},
			set(value) {
				this.$store.commit("setFinishedRequests", value);
			},
		},
	},
	methods: {
		...mapActions(["getFinishedRentingRequests", "postRentingReport"]),
		prepareForReport(request) {
			this.form.requestID = request.requestId;
			this.form.advertisementID = request.advertisementId;
			this.form.kilometersTraveled = undefined;
			this.form.content = undefined;
			this.active = true;
		},
		submitReport() {
			this.$store
				.dispatch("postRentingReport", this.form)
				.then((data) => {
					this.active = false;
					this.$store.dispatch("getFinishedRentingRequests");
				})
				.catch((error) => this.flashWarning(error.message, {timeout: 2000}));
		},
		validateForm() {
			this.$v.$touch();

			if (!this.$v.$invalid) {
				this.submitReport();
			}
		},
	},
	validations: {
		form: {
			kilometersTraveled: {
				required,
				integer,
			},
			content: {
				required,
				sqli,
			},
		},
	},
};
</script>

<style scoped>
#component {
	text-align: left;
}
.md-dialog /deep/ .md-dialog-container {
	/* width: 100%; */
	/* height: 110%; */
	width: 40%;
	max-height: 95%;
	position: absolute;
	/* left: 50%;
	top: 50%;
	transform: translate(-50%, -50%); */
}

.md-card {
	margin: 1.5%;
	display: inline-block;
	vertical-align: top;
	max-width: 90%;
}

.fade-enter-active,
.fade-leave-active {
	transition: opacity 1s;
}

.fade-enter,
.fate-leave-to {
	opacity: 0;
}

.card-expansion {
	margin: auto;
	width: 50%;
}

#left-div {
	width: 40%;
	float: left;
}

#right-div {
	width: 40%;
	float: right;
}
</style>
