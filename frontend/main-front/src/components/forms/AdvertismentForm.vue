<template>
	<div v-if="isLogged">
		<transition name="fade">
			<form v-if="show" class="md-layout md-alignment-top-center" autocomplete="off" @submit.prevent="validateAd">
				<md-card class="md-layout-item md-size-30 md-small-size-100">
					<md-card-header>
						<div class="title">Advertisment</div>
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
								<label for="kilometersTraveled">Kilometers Treveled</label>
								<md-input v-model="form.vehicle.kilometersTraveled" type="number" />
								<span class="md-error" v-if="!$v.form.vehicle.kilometersTraveled.required">Kilometers Trebeled is required</span>
								<span class="md-error" v-else-if="!$v.form.vehicle.kilometersTraveled.integer">Fuel Type must be integer</span>
							</md-field>
							<md-field :class="{ 'md-invalid': $v.form.vehicle.childSeatNumber.$error }">
								<label for="childSeatNumber">Childseat Number</label>
								<md-input v-model="form.vehicle.childSeatNumber" type="number" />
								<span class="md-error" v-if="!$v.form.vehicle.childSeatNumber.required">Childseat Number is required</span>
								<span class="md-error" v-else-if="!$v.form.vehicle.childSeatNumber.integer">Childseat Number must be integer</span>
							</md-field>
							<div>
								<md-checkbox v-model="form.vehicle.hasAndroid" class="md-primary">Has Android</md-checkbox>
								<md-checkbox v-model="form.collisionDamageWaiver" class="md-primary">Collision Damage Waiver</md-checkbox>
								<md-checkbox v-model="hasKilometersLimit" class="md-primary">Have Kilometers per day limit</md-checkbox>
							</div>
							<md-field v-if="hasKilometersLimit" :class="{ 'md-invalid': $v.form.kilometersPerDayLimit.$error }">
								<label for="kilometersPerDayLimit">Limit</label>
								<md-input v-model="form.kilometersPerDayLimit" type="number" />
								<span class="md-error" v-if="!$v.form.kilometersPerDayLimit.integer">Kilometers Per Day Limit must be integer</span>
							</md-field>
							<md-field :class="{ 'md-invalid': $v.form.price.$error }">
								<label for="price">Price</label>
								<span class="md-prefix">€</span>
								<md-input v-model="form.price" type="number" step="0.01"></md-input>
								<span class="md-error" v-if="!$v.form.price.required">Price is required</span>
								<span class="md-error" v-else-if="!$v.form.price.decimal">Price must be number</span>
							</md-field>
							<md-field :class="{ 'md-invalid': $v.form.description.$error }">
								<label for="description">Description</label>
								<md-textarea v-model="form.description"></md-textarea>
								<span class="md-error" v-if="!$v.form.description.required">Description is required</span>
								<span class="md-error" v-else-if="!$v.form.description.sqli">Description is not in proper format</span>
							</md-field>
						</div>
					</md-card-content>
					<md-card-actions>
						<md-button type="submit" class="md-primary" :disabled="sending">Submit</md-button>
					</md-card-actions>
				</md-card>
			</form>
		</transition>
	</div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { validationMixin } from "vuelidate";
import { required, integer, decimal } from "vuelidate/lib/validators";
import { helpers } from "vuelidate/lib/validators";

const sqli = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)([a-zA-Z0-9\\!\\?\\#\s?]+)$/);

export default {
	name: "AdvertismentForm",
	mixins: [validationMixin],
	data() {
		return {
			show: false,
			brandId: undefined,
			hasKilometersLimit: false,
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
				photos: [],
				price: undefined,
				kilometersPerDayLimit: undefined,
				collisionDamageWaiver: false,
				description: undefined,
				ownerId: undefined,
			},
			sending: false,
		};
	},
	mounted() {
		this.showMe();
	},
	computed: mapGetters(["isLogged", "getBrands", "getModels", "getVehicleClasses", "getGearboxTypes", "getFuelTypes", "getUser"]),
	methods: {
		...mapActions(["addAdvertisment"]),
		clearForm() {
			this.$v.$reset();
			this.brandId = undefined;
			this.hasKilometersLimit = false;
			this.form.photos = [];
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
		},

		showMe() {
			this.show = !this.show;
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
				.then(() => {})
				.catch((error) => console.log(error));
		},

		validateAd() {
			this.$v.$touch();

			if (!this.$v.$invalid) {
				this.submitAd();
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
			price: {
				required,
				decimal,
			},
			kilometersPerDayLimit: {
				integer,
			},
			description: {
				required,
				sqli,
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
</style>
