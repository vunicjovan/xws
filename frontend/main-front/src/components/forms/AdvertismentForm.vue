<template>
	<div v-if="isLogged && getUser !== null && (getUser.roles.includes('AGENT') || getUser.roles.includes('SIMPLE_USER'))">
		<flash-message class="myFlash"></flash-message>
		<form class="md-layout md-alignment-top-center" autocomplete="off" @submit.prevent="validateAd">
			<md-card class="md-layout-item md-size-30 md-small-size-100">
				<md-card-header>
					<div class="title">Advertisement</div>
				</md-card-header>
				<md-card-content>
					<div class="md-layout-item md-small-size-100">
						<md-field :class="{ 'md-invalid': $v.brandId.$error }">
							<label for="brand">Brand</label>
							<md-select name="brand" v-model="brandId">
								<md-option v-for="brand in getBrands" :key="brand.id" :value="brand.id">{{ brand.name }}</md-option>
							</md-select>
							<span class="md-error" v-if="!$v.brandId.required">Brand is required</span>
						</md-field>
						<md-field :class="{ 'md-invalid': $v.form.vehicle.modelId.$error }">
							<label for="model">Model</label>
							<md-select name="model" v-model="form.vehicle.modelId" :disabled="brandId == undefined">
								<div v-for="model in getModels" :key="model.id">
									<md-option v-if="model.brand.id == brandId" :value="model.id">{{ model.name }}</md-option>
								</div>
							</md-select>
							<span class="md-error" v-if="!$v.form.vehicle.modelId.required">Model is required</span>
						</md-field>
						<md-field :class="{ 'md-invalid': $v.form.vehicle.vehicleClassId.$error }">
							<label for="vehicleClass">Vehicle Class</label>
							<md-select name="vehicleClass" v-model="form.vehicle.vehicleClassId">
								<md-option v-for="vehicleClass in getVehicleClasses" :key="vehicleClass.id" :value="vehicleClass.id">{{ vehicleClass.name }}</md-option>
							</md-select>
							<span class="md-error" v-if="!$v.form.vehicle.vehicleClassId.required">Vehicle Class is required</span>
						</md-field>
						<md-field :class="{ 'md-invalid': $v.form.vehicle.gearboxTypeId.$error }">
							<label for="gearboxType">Gearbox Type</label>
							<md-select name="gearboxType" v-model="form.vehicle.gearboxTypeId">
								<md-option v-for="gearboxType in getGearboxTypes" :key="gearboxType.id" :value="gearboxType.id">{{ gearboxType.name }}</md-option>
							</md-select>
							<span class="md-error" v-if="!$v.form.vehicle.gearboxTypeId.required">Gearbox Type is required</span>
						</md-field>
						<md-field :class="{ 'md-invalid': $v.form.vehicle.fuelTypeId.$error }">
							<label for="fuelType">Fuel Type</label>
							<md-select name="fuelType" v-model="form.vehicle.fuelTypeId">
								<md-option v-for="fuelType in getFuelTypes" :key="fuelType.id" :value="fuelType.id">{{ fuelType.name }}</md-option>
							</md-select>
							<span class="md-error" v-if="!$v.form.vehicle.fuelTypeId.required">Fuel Type is required</span>
						</md-field>
						<md-field :class="{ 'md-invalid': $v.form.vehicle.kilometersTraveled.$error }">
							<label for="kilometersTraveled">Kilometers Traveled</label>
							<md-input v-model="form.vehicle.kilometersTraveled" type="number" min=0 />
							<span class="md-error" v-if="!$v.form.vehicle.kilometersTraveled.required">Kilometers Traveled is required</span>
							<span class="md-error" v-else-if="!$v.form.vehicle.kilometersTraveled.integer">Fuel Type must be integer</span>
						</md-field>
						<md-field :class="{ 'md-invalid': $v.form.vehicle.childSeatNumber.$error }">
							<label for="childSeatNumber">Childseat Number</label>
							<md-input v-model="form.vehicle.childSeatNumber" type="number" min=0 />
							<span class="md-error" v-if="!$v.form.vehicle.childSeatNumber.required">Childseat Number is required</span>
							<span class="md-error" v-else-if="!$v.form.vehicle.childSeatNumber.integer">Childseat Number must be integer</span>
						</md-field>
						<div>
							<md-checkbox v-model="form.vehicle.hasAndroid" class="md-primary">Has Android</md-checkbox>
							<md-checkbox v-model="form.collisionDamageWaiver" class="md-primary">Collision Damage Waiver</md-checkbox>
							<md-checkbox v-model="hasKilometersLimit" class="md-primary">Has kilometers per day limit</md-checkbox>
						</div>
						<md-field v-if="hasKilometersLimit" :class="{ 'md-invalid': $v.form.kilometersPerDayLimit.$error }">
							<label for="kilometersPerDayLimit">Limit</label>
							<md-input v-model="form.kilometersPerDayLimit" type="number" />
							<span class="md-error" v-if="!$v.form.kilometersPerDayLimit.integer">Kilometers Per Day Limit must be integer</span>
						</md-field>
						<md-field :class="{ 'md-invalid': $v.form.location.$error }">
							<label for="location">Location</label>
							<md-input v-model="form.location"></md-input>
							<span class="md-error" v-if="!$v.form.location.required">Location is required</span>
							<span class="md-error" v-else-if="!$v.form.location.lrx">Location is not in proper format</span>
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
							<md-textarea v-model="form.description"></md-textarea>
							<span class="md-error" v-if="!$v.form.description.required">Description is required</span>
							<span class="md-error" v-else-if="!$v.form.description.sqli">Description is not in proper format</span>
						</md-field>
						<md-field>
							<label>Photos</label>
							<md-file accept="image/*" ref="file" multiple @change="handleFileChange" />
						</md-field>
					</div>
				</md-card-content>
				<md-card-actions>
					<md-button type="submit" class="md-primary" :disabled="sending">Submit</md-button>
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
import { mapGetters, mapActions } from "vuex";
import { validationMixin } from "vuelidate";
import { required, integer, decimal } from "vuelidate/lib/validators";
import { helpers } from "vuelidate/lib/validators";

const sqli = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\s?]+)$/);
const lrx = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(([A-ZČĆŽŠĐ]){1,}[a-zčćšđžA-ZČĆŽŠĐ]+\s?)+$/);

export default {
	name: "AdvertismentForm",
	mixins: [validationMixin],
	data() {
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
	mounted() {
		this.$store
			.dispatch("getCatalog")
			.then(() => {})
			.catch((error) => console.log(error));

		if (this.getUser) this.$store.dispatch("getUsersPriceList", this.getUser.id);
	},
	computed: mapGetters(["isLogged", "getBrands", "getModels", "getVehicleClasses", "getGearboxTypes", "getFuelTypes", "getUser", "getPriceList"]),
	methods: {
		...mapActions(["addAdvertisment", "addPhotots", "getCatalog"]),
		clearForm() {
			this.$v.$reset();
			this.brandId = undefined;
			this.hasKilometersLimit = false;
			this.form.price = undefined;
			this.form.kilometersPerDayLimit = undefined;
			this.form.collisionDamageWaiver = false;
			this.form.description = undefined;
			this.form.ownerId = undefined;
			this.form.vehicle.modelId = undefined;
			this.form.vehicle.vehicleClassId = undefined;
			this.form.vehicle.fuelTypeId = undefined;
			this.form.vehicle.gearboxTypeId = undefined;
			this.form.vehicle.kilometersTraveled = undefined;
			this.form.vehicle.childSeatNumber = undefined;
			this.form.vehicle.hasAndroid = false;
			this.form.location = undefined;
			this.form.priceListItemId = undefined;
		},

		submitAd() {
			this.sending = true;

			window.setTimeout(() => {
				this.sending = false;
				this.clearForm();
			}, 1500);

			this.form.ownerId = this.getUser.id;
			if (!this.form.kilometersPerDayLimit) {
				this.form.kilometersPerDayLimit = -1;
			}

			this.$store
				.dispatch("addAdvertisment", this.form)
				.then((ad) => {
					let formData = new FormData();
					let id = ad.id;
					for (var i = 0; i < this.photos.length; i++) {
						let file = this.photos[i];

						formData.append("files", file);
					}

					let payload = {
						id: id,
						photos: formData,
					};

					this.$store
						.dispatch("addPhotots", payload)
						.then(() => {
							this.$router.push("/");
						})
						.catch((error) => this.flashWarning(error, {timeout: 2000}));
				})
				.catch((error) => this.flashWarning(error, {timeout: 2000}));
		},

		handleFileChange() {
			this.photos = this.$refs.file.$refs.inputFile.files;
		},

		validateAd() {
			this.$v.form.$touch();
			this.$v.brandId.$touch();

			if (!this.$v.form.$invalid && !this.$v.brandId.$invalid) {
				this.submitAd();
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
					this.pricelistForm.dailyPrice = undefined;
					this.pricelistForm.cdwPrice = undefined;
					this.pricelistForm.debtPrice = undefined;
					this.active = false;
				})
				.catch((error) => this.flashWarning(error.message, {timeout: 2000}));
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
		brandId: {
			required,
		},
		form: {
			vehicle: {
				modelId: {
					required,
				},
				vehicleClassId: {
					required,
				},
				gearboxTypeId: {
					required,
				},
				fuelTypeId: {
					required,
				},
				kilometersTraveled: {
					required,
					integer,
				},
				childSeatNumber: {
					required,
					integer,
				},
			},
			location: {
				required,
				lrx,
			},
			priceListItemId: {
				required,
			},
			kilometersPerDayLimit: {
				integer,
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
.fade-enter-active,
.fade-leave-active {
	transition: opacity 2s;
}

.md-checkbox {
	display: flex;
}

.fade-enter,
.fade-leave-to {
	opacity: 0;
}

.outer {
	display: flex;
	align-items: baseline;
}

.full {
	flex: 1;
}
</style>
