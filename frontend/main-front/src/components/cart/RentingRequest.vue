<template>
	<div>
		<md-card>
			<md-card-header>
				<h2>Renting requests</h2>
			</md-card-header>
			<md-card-content>
				<md-switch v-model="batch" class="md-primary">Batch</md-switch>
				<div v-if="!batch">
					<div v-for="(ad, index) in groups" :key="index">
						<md-card class="md-card-inner">
							<md-card-header>
								<h3>Request {{ index + 1 }}</h3>
							</md-card-header>
							<md-card-content>
								{{ ad.brand }} {{ ad.model }}, {{ ad.location }}, {{ ad.price }}€
								<md-datepicker v-model="startDates[index]" :md-immediately="true">
									<label>Start date</label>
								</md-datepicker>
								<md-datepicker v-model="endDates[index]" :md-immediately="true">
									<label>End date</label>
								</md-datepicker>
							</md-card-content>
						</md-card>
					</div>
				</div>
				<div v-else>
					<div v-for="(value, name, index) in groups" :key="index">
						<md-card class="md-card-inner">
							<md-card-header>
								<h3>Group {{ index + 1 }}</h3>
							</md-card-header>
							<md-card-content>
								<div v-for="ad in value" :key="ad.id">{{ ad.brand }} {{ ad.model }}, {{ ad.location }}, {{ ad.price }}€</div>
								<md-datepicker v-model="startDates[name]" :md-immediately="true">
									<label>Start date</label>
								</md-datepicker>
								<md-datepicker v-model="endDates[name]" :md-immediately="true">
									<label>End date</label>
								</md-datepicker>
							</md-card-content>
						</md-card>
					</div>
				</div>
				<md-snackbar :md-active.sync="dateInput">All dates must be entered</md-snackbar>
			</md-card-content>
			<md-card-actions>
				<md-button class="md-primary" @click="sendRequests()">Send</md-button>
			</md-card-actions>
		</md-card>
	</div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { validationMixin } from "vuelidate";
import { required, integer, decimal } from "vuelidate/lib/validators";
export default {
	data() {
		return {
			batch: false,
			startDates: {},
			endDates: {},
			dateInput: false,
		};
	},
	computed: {
		...mapGetters(["getCartAdvertisements", "getUser"]),
		groups: {
			get() {
				if (this.batch) {
					return groupBy(this.getCartAdvertisements, "ownerId");
				} else {
					return this.getCartAdvertisements;
				}
			},
			set(advertisements) {
				this.$store.commit("setCartAdvertisements", advertisements);
			},
		},
	},
	methods: {
		...mapActions(["sendRequest"]),
		updateData(value, e) {
			console.log(value);
			console.log(e);
		},
		sendRequests() {
			if (!this.batch) {
				this.groups.forEach((value, index) => {
					let advertisementIDs = [];
					advertisementIDs.push(value.id);
					if (!(this.startDates[index] && this.endDates[index])) {
						this.dateInput = true;
						return;
					}
					let form = {
						startDate: this.startDates[index],
						endDate: this.endDates[index],
						senderId: this.getUser.id,
						advertisementIDs: advertisementIDs,
					};
					this.$store
						.dispatch("sendRequest", form)
						.then((data) => console.log(data))
						.catch((error) => console.log(error));
				});
			} else {
				for (let key in this.groups) {
					if (!(this.startDates[key] && this.endDates[key])) {
						this.dateInput = true;
						return;
					}
					let adverts = [];
					this.groups[key].forEach((value) => {
						adverts.push(value.id);
					});
					let form = {
						startDate: this.startDates[key],
						endDate: this.endDates[key],
						senderId: this.getUser.id,
						advertisementIDs: adverts,
					};
					this.$store
						.dispatch("sendRequest", form)
						.then((data) => console.log(data))
						.catch((error) => console.log(error));
				}
			}
		},
	},
	validations: {
		startDates: {
			required,
		},
		endDates: {
			required,
		},
	},
};

function groupBy(array, key) {
	const result = {};
	array.forEach((item) => {
		if (!result[item[key]]) {
			result[item[key]] = [];
		}
		result[item[key]].push(item);
	});
	return result;
}
</script>

<style scoped>
.md-card-inner {
	margin-top: 1.5%;
	margin-bottom: 1.5%;
	margin-left: 0%;
	margin-right: 0%;
}
</style>
