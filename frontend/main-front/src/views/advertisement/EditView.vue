<template>
	<div v-if="isLogged && getUser !== null && getAdvertisement !== null" class="md-layout md-alignment-center-center">
		<form class="md-layout md-alignment-top-center" autocomplete="off" @submit.prevent="validateAd">
			<md-card class="md-layout-item md-size-30 md-small-size-100">
				<md-card-header>
					<div class="title">Advertisement</div>
				</md-card-header>
				<md-card-content>
					<div class="md-layout-item md-small-size-100">
						<md-field>
							<label for="brand">Brand: {{ getAdvertisement.brand }}</label>
						</md-field>
						<md-field>
							<label for="model">Model: {{ getAdvertisement.model }}</label>
						</md-field>
						<md-field>
							<label for="vehicleClass">Vehicle Class: {{ getAdvertisement.vehicleClass }}</label>
						</md-field>
						<md-field>
							<label for="gearboxType">Gearbox Type: {{ getAdvertisement.gearbox }}</label>
						</md-field>
						<md-field>
							<label for="fuelType">Fuel Type: {{ getAdvertisement.fuel }}</label>
						</md-field>
						<md-field>
							<label for="kilometersTraveled">Kilometers Traveled: {{ getAdvertisement.kmTraveled }}</label>
						</md-field>
						<md-field>
							<label for="childSeatNumber">Childseat Number: {{ getAdvertisement.childSeatNumber }}</label>
						</md-field>
						<div>
							<md-checkbox v-model="getAdvertisement.android" class="md-primary" disabled>Has Android</md-checkbox>
							<md-checkbox v-model="getAdvertisement.cdw" class="md-primary" disabled>Collision Damage Waiver</md-checkbox>
							<md-checkbox v-model="hasKmLimit" class="md-primary" disabled>Has kilometers per day limit</md-checkbox>
						</div>
						<md-field v-if="getAdvertisement.dailyLimit !== -1">
							<label for="kilometersPerDayLimit">Limit: {{ getAdvertisement.dailyLimit }}</label>
						</md-field>
						<md-field>
							<label for="location">Location: {{ getAdvertisement.location }}</label>
						</md-field>
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
							<md-textarea v-model="adDescription"></md-textarea>
							<span class="md-error" v-if="!$v.form.description.required">Description is required</span>
							<span class="md-error" v-else-if="!$v.form.description.sqli">Description is not in proper format</span>
						</md-field>
					</div>
				</md-card-content>
				<md-card-actions>
					<md-button type="submit" class="md-primary" :disabled="sending">Update</md-button>
					<md-button @click="$router.push('/single-ad/' + getAdvertisement.id)" class="md-accent">Cancel</md-button>
				</md-card-actions>
			</md-card>
		</form>

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
import { validationMixin } from "vuelidate";
import { required, integer, decimal } from "vuelidate/lib/validators";
import { helpers } from "vuelidate/lib/validators";

import { mapGetters, mapActions } from "vuex";

const sqli = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\s?]+)$/);

export default {
	name: "EditView",

	mixins: [validationMixin],

	data: function() {
		return {
			active: false,
			brandId: undefined,
			hasKilometersLimit: false,
			photos: [],
			form: {
				vehicle: {
					modeld: undefined,
					vehicleClassId: undefined,
					fuelTypeId: undefined,
					gearboxTypeId: undefined,
					kilometersTraveled: undefined,
					childSeatNumber: undefined,
					hasAndroid: false,
				},
				price: undefined,
				kilometersPerDayLimit: undefined,
				collisionDamageWaiver: false,
				location: undefined,
				description: undefined,
				ownerId: undefined,
				priceListItemId: undefined,
			},
			pricelistForm: {
				dailyPrice: undefined,
				cdwPrice: undefined,
				debtPrice: undefined,
				creatorId: undefined,
			},
			sending: false,
		};
	},

	mounted: function() {
		this.$store.dispatch("getDetailedAdvertisement", this.$route.params.id);
		if (this.getUser) this.$store.dispatch("getUsersPriceList", this.getUser.id);
	},

	computed: {
		...mapGetters(["getAdvertisement", "getUser", "isLogged", "getPriceList"]),
		hasKmLimit: {
			get() {
				if (this.getAdvertisement.dailyLimit !== -1) {
					return true;
				} else {
					return false;
				}
			},
		},
		adPrice: {
			get() {
				return this.getAdvertisement.price;
			},
			set(p) {
				this.form.price = p;
			},
		},
		adDescription: {
			get() {
				return this.getAdvertisement.description;
			},
			set(d) {
				this.form.description = d;
			},
		},
	},

	methods: {
		...mapActions(["getDetailedAdvertisement"]),
		getPhotoURL(advertisementId, photoName) {
			return `http://localhost:8089/agent/images/${advertisementId}/${photoName}/`;
		},
		updateAd(adId) {
			let ad = {
				adId: adId,
				data: {
					priceListItemId: this.form.priceListItemId,
					description: this.form.description,
				},
			};

			this.$store
				.dispatch("putAdvertisement", ad)
				.then((data) => {
					this.$router.push("/single-ad/" + this.getAdvertisement.id);
				})
				.catch((error) => console.log(error));
		},
		cancelDialog() {
			this.active = false;
			this.$v.$reset();
			this.price = undefined;
			this.description = undefined;
		},
		validateAd() {
			this.$v.form.$touch();

			if (!this.$v.form.$invalid) {
				this.updateAd(this.getAdvertisement.id);
			}
		},
		validatePriceListItem() {
			this.$v.pricelistForm.$touch();

			if (!this.$v.pricelistForm.dailyPrice.$invalid && !this.$v.pricelistForm.cdwPrice.$invalid && !this.$v.pricelistForm.debtPrice.$invalid) {
				this.submitPriceListItem();
			}
		},
		submitPriceListItem() {
			this.pricelistForm.creatorId = this.getUser.id;

			this.$store
				.dispatch("addNewPriceListItem", this.pricelistForm)
				.then((data) => {
					this.$v.$reset;
					this.pricelistForm.dailyPrice = undefined;
					this.pricelistForm.cdwPrice = undefined;
					this.pricelistForm.debtPrice = undefined;
					this.active = false;
				})
				.catch((error) => console.log(error));
		},
	},

	watch: {
		getUser: function(val) {
			if (val) {
				this.$store.dispatch("getUsersPriceList", val.id);
			}
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

<style>
.md-card {
	width: 70%;
	margin: 5%;
	padding: 1%;
	display: inline-block;
	vertical-align: top;
}

.images,
.hooper {
	width: 60%;
	height: 10%;
	display: block;
	margin-left: auto;
	margin-right: auto;
}

.outer {
	display: flex;
	align-items: baseline;
}

.full {
	flex: 1;
}

.img1 {
	border-radius: 15%;
}
</style>
