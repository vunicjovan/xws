<template>
	<div>
		<md-button @click="showSearchDialog = true">
			<i class="fas fa-search fa-2x"></i>
			<md-tooltip>Search advertisements</md-tooltip>
		</md-button>
		<md-dialog @md-opened="openSearchDialog()" :md-active.sync="showSearchDialog">
			<md-dialog-title>Search</md-dialog-title>

			<md-tabs md-dynamic-height md-dynamic-width>
				<!-- <md-tabs> -->
				<md-tab md-label="Simple" @click="searchAdvanced = false">
					<md-dialog-content>
						<md-datepicker :class="{ 'md-invalid': $v.startDate.$error }" v-model="startDate">
							<label>Select start date</label>
							<span class="md-error" v-if="!$v.startDate.required">Start date is required</span>
						</md-datepicker>
						<md-datepicker :class="{ 'md-invalid': $v.endDate.$error }" v-model="endDate">
							<label>Select end date</label>
							<span class="md-error" v-if="!$v.endDate.required">End date is required</span>
						</md-datepicker>
						<md-field :class="{ 'md-invalid': $v.address.$error }">
							<label>Address</label>
							<md-input v-model="address"></md-input>
							<span class="md-error" v-if="!$v.address.required">Address is required</span>
							<span class="md-error" v-else-if="!$v.address.lrx">Address must contain capital words</span>
						</md-field>
					</md-dialog-content>
				</md-tab>
				<md-tab md-label="Advanced" @click="searchAdvanced = true">
					<md-dialog-content>
						<div>
							<div id="left-div">
								<md-datepicker :class="{ 'md-invalid': $v.startDate.$error }" v-model="startDate">
									<label>Select start date</label>
									<span class="md-error" v-if="!$v.startDate.required">Start date is required</span>
								</md-datepicker>
								<md-datepicker :class="{ 'md-invalid': $v.endDate.$error }" v-model="endDate">
									<label>Select end date</label>
									<span class="md-error" v-if="!$v.endDate.required">End date is required</span>
								</md-datepicker>
								<md-field :class="{ 'md-invalid': $v.address.$error }">
									<label>Address</label>
									<md-input v-model="address"></md-input>
									<span class="md-error" v-if="!$v.address.required">Address is required</span>
								</md-field>
								<md-field>
									<label for="brand">Brand</label>
									<md-select name="brand" v-model="brandId">
										<md-option v-for="brand in getBrands" :key="brand.id" :value="brand.id">{{ brand.name }}</md-option>
									</md-select>
								</md-field>
								<md-field>
									<label for="model">Model</label>
									<md-select name="model" v-model="modelId" :disabled="brandId == undefined">
										<div v-for="model in getModels" :key="model.id">
											<md-option v-if="model.brand.id == brandId" :value="model.id">{{ model.name }}</md-option>
										</div>
									</md-select>
								</md-field>
								<md-field>
									<label for="fuelType">Fuel Type</label>
									<md-select name="fuelType" v-model="fuelTypeId">
										<md-option v-for="fuelType in getFuelTypes" :key="fuelType.id" :value="fuelType.id">{{ fuelType.name }}</md-option>
									</md-select>
								</md-field>
								<md-field>
									<label for="gearboxType">Gearbox Type</label>
									<md-select name="gearboxType" v-model="gearboxTypeId">
										<md-option v-for="gearboxType in getGearboxTypes" :key="gearboxType.id" :value="gearboxType.id">{{ gearboxType.name }}</md-option>
									</md-select>
								</md-field>
							</div>
							<div id="right-div">
								<md-field>
									<label for="vehicleClass">Vehicle Class</label>
									<md-select name="vehicleClass" v-model="vehicleClassId">
										<md-option v-for="vehicleClass in getVehicleClasses" :key="vehicleClass.id" :value="vehicleClass.id">{{ vehicleClass.name }}</md-option>
									</md-select>
								</md-field>
								<md-field>
									<label for="minPrice">Minimum Price</label>
									<md-input v-model="minPrice" type="number" step="0.01" min="0" />
									<span class="md-error" v-if="!$v.minPrice.decimal">Minimum Price must be decimal</span>
									<span class="md-error" v-else-if="!$v.minPrice.between">Min Price must be lower than Max Price</span>
								</md-field>
								<md-field>
									<label for="maxPrice">Maximum Price</label>
									<md-input v-model="maxPrice" type="number" step="0.01" min="0" />
									<span class="md-error" v-if="!$v.maxPrice.decimal">Maximum Price must be decimal</span>
									<span class="md-error" v-else-if="!$v.maxPrice.between">Max Price must be higher than Min Price</span>
								</md-field>
								<md-field>
									<label for="kilometersTraveled">Kilometers Traveled</label>
									<md-input v-model="kilometersTraveled" type="number" min="0" />
									<span class="md-error" v-if="!$v.kilometersTraveled.integer">Kilometers Traveled must be integer</span>
								</md-field>
								<md-field>
									<label for="kilometersTraveled">Kilometers Planed</label>
									<md-input v-model="kilometersPlaned" type="number" min="0" />
									<span class="md-error" v-if="!$v.kilometersPlaned.integer">Kilometers Planed must be integer</span>
								</md-field>
								<md-checkbox v-model="collisionDamageWaiver" class="md-primary">Collision Damage Waiver</md-checkbox>
								<md-field>
									<label for="childSeatNumber">Childseat Number</label>
									<md-input v-model="childSeatNumber" type="number" min="0" />
									<span class="md-error" v-if="!$v.childSeatNumber.integer">Childseat Number must be integer</span>
								</md-field>
							</div>
						</div>
					</md-dialog-content>
				</md-tab>
			</md-tabs>
			<md-dialog-actions>
				<md-button class="md-primary" @click.prevent="validateSearchParams">Search</md-button>
				<md-button class="md-primary" @click="showSearchDialog = false">Close</md-button>
			</md-dialog-actions>
		</md-dialog>
	</div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { validationMixin } from "vuelidate";
import { required, integer, decimal, between, helpers } from "vuelidate/lib/validators";

const lrx = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(([A-ZČĆŽŠĐ]){1,}[a-zčćšđžA-ZČĆŽŠĐ]+\s?)+$/);

export default {
	mixins: [validationMixin],
	data() {
		return {
			searchAdvanced: false,
			showSearchDialog: false,
			startDate: undefined,
			endDate: undefined,
			address: undefined,
			brandId: undefined,
			modelId: undefined,
			fuelTypeId: undefined,
			gearboxTypeId: undefined,
			vehicleClassId: undefined,
			minPrice: 0,
			maxPrice: 0,
			kilometersTraveled: undefined,
			kilometersPlaned: undefined,
			collisionDamageWaiver: false,
			childSeatNumber: undefined,
		};
	},
	mounted() {
		this.$store
			.dispatch("getCatalog")
			.then(() => {})
			.catch((error) => console.log(error));
	},
	computed: {
		...mapGetters(["getBrands", "getModels", "getVehicleClasses", "getGearboxTypes", "getFuelTypes"]),
		retMaxPrice() {
			return this.maxPrice;
		},
		retMinPrice() {
			return this.minPrice;
		},
	},
	methods: {
		...mapActions(["getCatalog", "simpleSearch", "advancedSearch"]),
		openSearchDialog: function() {
			this.$v.$reset();
			this.startDate = undefined;
			this.endDate = undefined;
			this.address = undefined;
			this.brandId = null;
			this.modelId = null;
			this.fuelTypeId = null;
			this.gearboxTypeId = null;
			this.vehicleClassId = null;
			this.minPrice = 0;
			this.maxPrice = 0;
			this.kilometersTraveled = null;
			this.kilometersPlaned = null;
			this.collisionDamageWaiver = false;
			this.childSeatNumber = null;
		},
		validateSearchParams: function() {
			this.$v.$touch();

			if (!this.$v.$invalid) {
				this.showSearchDialog = false;
				this.searchForAd();
				this.searchAdvanced = false;
			}
		},
		searchForAd: function() {
			if (!this.searchAdvanced) {
				let parameters = new URLSearchParams();
				parameters.append("address", this.address);
				parameters.append("startDate", this.startDate);
				parameters.append("endDate", this.endDate);

				this.$store
					.dispatch("simpleSearch", parameters)
					.then(() => console.log("Simple search done"))
					.catch((error) => console.log(error));
			} else {
				let parameters = new URLSearchParams();
				parameters.append("address", this.address);
				parameters.append("startDate", this.startDate);
				parameters.append("endDate", this.endDate);
				if (this.brandId) parameters.append("brand", this.brandId);
				if (this.modelId) parameters.append("model", this.modelId);
				if (this.fuelTypeId) parameters.append("fuel", this.fuelTypeId);
				if (this.gearboxTypeId) parameters.append("gearbox", this.gearboxTypeId);
				if (this.vehicleClassId) parameters.append("class", this.vehicleClassId);
				if (this.minPrice) parameters.append("minPrice", this.minPrice);
				if (this.maxPrice) parameters.append("maxPrice", this.maxPrice);
				if (this.kilometersTraveled) parameters.append("kmTraveled", this.kilometersTraveled);
				if (this.kilometersPlaned) parameters.append("kmPlaned", this.kilometersPlaned);
				if (this.collisionDamageWaiver) parameters.append("cdw", this.collisionDamageWaiver);
				if (this.childSeatNumber) parameters.append("childrenSeatNum", this.childSeatNumber);

				this.$store
					.dispatch("advancedSearch", parameters)
					.then(() => console.log("Simple search done"))
					.catch((error) => console.log(error));
			}
		},
	},
	validations: {
		startDate: {
			required,
		},
		endDate: {
			required,
		},
		address: {
			required,
			lrx,
		},
		kilometersTraveled: {
			integer,
		},
		kilometersPlaned: {
			integer,
		},
		minPrice: {
			decimal,
			between(value) {
				return between(0, this.retMaxPrice)(value);
			},
		},
		maxPrice: {
			decimal,
			between(value) {
				return between(this.retMinPrice, 999999999)(value);
			},
		},
		childSeatNumber: {
			integer,
		},
	},
};
</script>

<style scoped>
.md-dialog /deep/ .md-dialog-container {
	/* width: 100%; */
	/* height: 110%; */
	max-height: 95%;
	position: absolute;
	left: 50%;
	top: 50%;
	transform: translate(-50%, -50%);
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
