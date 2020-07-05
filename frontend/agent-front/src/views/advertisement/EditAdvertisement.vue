<template>
	<div v-if="getAdvertisement" class="md-layout md-alignment-center">
		<md-card class="md-layout-item md-size-60">
			<md-card-header>
				<md-card-header-text>
					<div>
						<p class="md-title">{{ getAdvertisement.brand }} {{ getAdvertisement.model }}</p>
						<p class="md-subtitle">Current price: {{ getAdvertisement.price }}€</p>
						<p class="md-subhead">{{ getAdvertisement.location }}</p>
					</div>
				</md-card-header-text>
			</md-card-header>

			<md-card-content>
				<div class="outer">
					<md-field :class="{ 'md-invalid': $v.form.priceListItemId.$error }" class="full">
						<label for="price">Price</label>
						<md-select name="price" v-model="form.priceListItemId">
							<md-option v-for="pricelist in getPriceList" :key="pricelist.id" :value="pricelist.id"
								>Daily={{ pricelist.dailyPrice }}€, CDW={{ pricelist.cdwPrice }}€, Debt={{ pricelist.debtPrice }}€</md-option
							>
						</md-select>
						<span class="md-error" v-if="!$v.form.priceListItemId.required">Price is required field</span>
					</md-field>
					<md-button class="md-primary" @click.prevent="active = true">Add</md-button>
				</div>
				<md-field :class="{ 'md-invalid': $v.form.description.$error }">
					<label for="description">Description</label>
					<md-textarea v-model="form.description"></md-textarea>
					<span class="md-error" v-if="!$v.form.description.required">Description is required</span>
					<span class="md-error" v-else-if="!$v.form.description.sqli">Description is not in proper format</span>
				</md-field>
			</md-card-content>

			<md-card-actions>
				<md-button class="md-primary" @click.prevent="validateForm">SEND</md-button>
			</md-card-actions>
		</md-card>

		<md-dialog :md-active.sync="active">
			<md-dialog-title>New Price List Item</md-dialog-title>
			<md-dialog-content>
				<form>
					<md-field :class="{ 'md-invalid': $v.pricelistForm.dailyPrice.$error }">
						<label>Daily price</label>
						<md-input type="number" v-model="pricelistForm.dailyPrice" step="0.01" />
						<span class="md-error" v-if="!$v.pricelistForm.dailyPrice.required">Daily price is required</span>
					</md-field>
					<md-field :class="{ 'md-invalid': $v.pricelistForm.cdwPrice.$error }">
						<label>Collision damage waiver</label>
						<md-input type="number" v-model="pricelistForm.cdwPrice" step="0.01" />
						<span class="md-error" v-if="!$v.pricelistForm.cdwPrice.required">Collision damage waiver price is required</span>
					</md-field>
					<md-field :class="{ 'md-invalid': $v.pricelistForm.debtPrice.$error }">
						<label>Debt price</label>
						<md-input type="number" v-model="pricelistForm.debtPrice" step="0.01" />
						<span class="md-error" v-if="!$v.pricelistForm.debtPrice.required">Debt price is required</span>
					</md-field>
				</form>
			</md-dialog-content>
			<md-dialog-actions>
				<md-button class="md-primary" @click="active = false">Cancel</md-button>
				<md-button class="md-primary" @click.prevent="validatePriceListItem">Add</md-button>
			</md-dialog-actions>
		</md-dialog>
	</div>
</template>

<script>
import { mapGetters } from "vuex";
import { validationMixin } from "vuelidate";
import { required, integer, decimal } from "vuelidate/lib/validators";
import { helpers } from "vuelidate/lib/validators";

const sqli = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\s?]+)$/);
export default {
	neme: "EditAdvertisement",
	mixins: [validationMixin],
	mounted() {
		this.$store
			.dispatch("getDetailedAdvertisement", this.$route.params.id)
			.then(() => {
				this.form.description = this.getAdvertisement.description;
				this.form.advertisementId = this.getAdvertisement.id;
			})
			.catch((error) => console.log(error));
	},
	data() {
		return {
			active: false,
			form: {
				priceListItemId: undefined,
				description: undefined,
				advertisementId: undefined,
			},
			pricelistForm: {
				dailyPrice: undefined,
				cdwPrice: undefined,
				debtPrice: undefined,
				creatorId: 1,
			},
		};
	},
	computed: {
		...mapGetters(["getAdvertisement", "getPriceList"]),
	},
	methods: {
		validateForm() {
			this.$v.form.$touch();

			if (!this.$v.form.$invalid) {
				this.submitForm();
			}
		},
		submitForm() {
			this.$store.dispatch();
		},
		validatePriceListItem() {
			this.$v.pricelistForm.$touch();

			if (!this.$v.pricelistForm.dailyPrice.$invalid && !this.$v.pricelistForm.cdwPrice.$invalid && !this.$v.pricelistForm.debtPrice.$invalid) {
				this.submitPriceListItem();
			}
		},
		submitPriceListItem() {
			this.$store
				.dispatch("newPriceListItem", this.pricelistForm)
				.then((data) => {
					this.pricelistForm.dailyPrice = undefined;
					this.pricelistForm.cdwPrice = undefined;
					this.pricelistForm.debtPrice = undefined;
					this.active = false;
				})
				.catch((error) => console.log(error));
		},
	},
	validations: {
		form: {
			priceListItemId: {
				required,
			},
			description: {
				required,
				sqli,
			},
		},
		pricelistForm: {
			dailyPrice: {
				required,
			},
			cdwPrice: {
				required,
			},
			debtPrice: {
				required,
			},
		},
	},
};
</script>

<style scoped>
.md-card {
	margin: 2.5%;
	display: inline-block;
	vertical-align: top;
}
.outer {
	display: flex;
	align-items: baseline;
}

.full {
	flex: 1;
}
</style>
