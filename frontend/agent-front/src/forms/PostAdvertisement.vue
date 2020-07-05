<template>
	<div>
		<md-steppers :md-active-step.sync="active" md-linear>
			<md-step id="first" md-label="First Step" :md-done.sync="first">
				<form class="md-layout md-alignment-top-center" autocomplete="off">
					<md-field :class="{ 'md-invalid': $v.brandId.$error }">
						<label for="brand">Brand</label>
						<md-select name="brand" v-model="brandId">
							<md-option v-for="brand in getBrands" :key="brand.id" :value="brand.id">{{ brand.name }}</md-option>
						</md-select>
						<span class="md-error" v-if="!$v.brandId.required">Brand is required</span>
					</md-field>
					<md-field :class="{ 'md-invalid': $v.form1.vehicle.modelId.$error }">
						<label for="model">Model</label>
						<md-select name="model" v-model="form1.vehicle.modelId" :disabled="brandId == undefined">
							<div v-for="model in getModels" :key="model.id">
								<md-option v-if="model.brand.id == brandId" :value="model.id">{{ model.name }}</md-option>
							</div>
						</md-select>
						<span class="md-error" v-if="!$v.form1.vehicle.modelId.required">Model is required</span>
					</md-field>
					<md-field :class="{ 'md-invalid': $v.form1.vehicle.vehicleClassId.$error }">
						<label for="vehicleClass">Vehicle Class</label>
						<md-select name="vehicleClass" v-model="form1.vehicle.vehicleClassId">
							<md-option v-for="vehicleClass in getVehicleClasses" :key="vehicleClass.id" :value="vehicleClass.id">{{ vehicleClass.name }}</md-option>
						</md-select>
						<span class="md-error" v-if="!$v.form1.vehicle.vehicleClassId.required">Vehicle Class is required</span>
					</md-field>
					<md-field :class="{ 'md-invalid': $v.form1.vehicle.gearboxTypeId.$error }">
						<label for="gearboxType">Gearbox Type</label>
						<md-select name="gearboxType" v-model="form1.vehicle.gearboxTypeId">
							<md-option v-for="gearboxType in getGearboxTypes" :key="gearboxType.id" :value="gearboxType.id">{{ gearboxType.name }}</md-option>
						</md-select>
						<span class="md-error" v-if="!$v.form1.vehicle.gearboxTypeId.required">Gearbox Type is required</span>
					</md-field>
					<md-field :class="{ 'md-invalid': $v.form1.vehicle.fuelTypeId.$error }">
						<label for="fuelType">Fuel Type</label>
						<md-select name="fuelType" v-model="form1.vehicle.fuelTypeId">
							<md-option v-for="fuelType in getFuelTypes" :key="fuelType.id" :value="fuelType.id">{{ fuelType.name }}</md-option>
						</md-select>
						<span class="md-error" v-if="!$v.form1.vehicle.fuelTypeId.required">Fuel Type is required</span>
					</md-field>
				</form>
				<md-button @click="validateAd1" class="md-raised md-primary">Continue</md-button>
			</md-step>

			<md-step id="second" md-label="Second Step" :md-done.sync="second">
				<form class="md-layout md-alignment-top-center" autocomplete="off">
					<md-field :class="{ 'md-invalid': $v.form2.vehicle.kilometersTraveled.$error }">
						<label for="kilometersTraveled">Kilometers Traveled</label>
						<md-input v-model="form2.vehicle.kilometersTraveled" type="number" />
						<span class="md-error" v-if="!$v.form2.vehicle.kilometersTraveled.required">Kilometers Traveled is required</span>
						<span class="md-error" v-else-if="!$v.form2.vehicle.kilometersTraveled.integer">Fuel Type must be integer</span>
					</md-field>
					<md-field :class="{ 'md-invalid': $v.form2.vehicle.childSeatNumber.$error }">
						<label for="childSeatNumber">Childseat Number</label>
						<md-input v-model="form2.vehicle.childSeatNumber" type="number" />
						<span class="md-error" v-if="!$v.form2.vehicle.childSeatNumber.required">Childseat Number is required</span>
						<span class="md-error" v-else-if="!$v.form2.vehicle.childSeatNumber.integer">Childseat Number must be integer</span>
					</md-field>
					<div>
						<md-checkbox v-model="form2.vehicle.hasAndroid" class="md-primary">Has Android</md-checkbox>
						<md-checkbox v-model="form2.collisionDamageWaiver" class="md-primary">Collision Damage Waiver</md-checkbox>
						<md-checkbox v-model="hasKilometersLimit" class="md-primary">Has kilometers per day limit</md-checkbox>
					</div>
					<md-field v-if="hasKilometersLimit" :class="{ 'md-invalid': $v.form2.kilometersPerDayLimit.$error }">
						<label for="kilometersPerDayLimit">Limit</label>
						<md-input v-model="form2.kilometersPerDayLimit" type="number" />
						<span class="md-error" v-if="!$v.form2.kilometersPerDayLimit.integer">Kilometers Per Day Limit must be integer</span>
					</md-field>
					<md-field :class="{ 'md-invalid': $v.form2.location.$error }">
						<label for="location">Location</label>
						<md-input v-model="form2.location"></md-input>
						<span class="md-error" v-if="!$v.form2.location.required">Location is required</span>
						<span class="md-error" v-else-if="!$v.form2.location.lrx">Location is not in proper format</span>
					</md-field>
					<md-field :class="{ 'md-invalid': $v.form2.priceListItemDTO.id.$error }">
						<label for="price">Price</label>
						<md-select name="price" v-model="form2.priceListItemDTO.id">
							<md-option v-for="pricelist in getPriceList" :key="pricelist.id" :value="pricelist.id"
								>Daily={{ pricelist.dailyPrice }}€, CDW={{ pricelist.cdwPrice }}€, Debt={{ pricelist.debtPrice }}€</md-option
							>
						</md-select>
						<span class="md-error" v-if="!$v.form2.priceListItemDTO.id.required">Price is required field</span>
						<md-button class="md-primary" @click.prevent="newPriceList = true">Add</md-button>
					</md-field>

					<md-button @click="validateAd2" class="md-raised md-primary">Continue</md-button>
				</form>
			</md-step>

			<md-step id="third" md-label="Third Step" :md-done.sync="third">
				<form class="md-layout md-alignment-top-center" autocomplete="off">
					<md-field :class="{ 'md-invalid': $v.form3.description.$error }">
						<label for="description">Description</label>
						<md-textarea v-model="form3.description"></md-textarea>
						<span class="md-error" v-if="!$v.form3.description.required">Description is required</span>
						<span class="md-error" v-else-if="!$v.form3.description.sqli">Description is not in proper format</span>
					</md-field>
					<md-field>
						<label>Photos</label>
						<md-file accept="image/*" ref="file" multiple @change="handleFileChange" />
					</md-field>
					<md-button @click="validateAd3" class="md-raised md-primary" :disabled="sending">Done</md-button>
				</form>
			</md-step>
		</md-steppers>

		<md-dialog :md-active.sync="newPriceList">
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
				<md-button class="md-primary" @click="newPriceList = false">Cancel</md-button>
				<md-button class="md-primary" @click.prevent="validatePriceListItem">Add</md-button>
			</md-dialog-actions>
		</md-dialog>
	</div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { validationMixin } from "vuelidate";
import { required, integer, decimal, helpers } from "vuelidate/lib/validators";

const sqli = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\s?]+)$/);
const lrx = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(([A-ZČĆŽŠĐ]){1,}[a-zčćšđžA-ZČĆŽŠĐ]+\s?)+$/);

export default {
	name: "PostAdvertisement",
	mixins: [validationMixin],
	data() {
		return {
			show: false,
			brandId: undefined,
			hasKilometersLimit: false,
			photos: [],
			form1: {
				vehicle: {
					modeld: undefined,
					vehicleClassId: undefined,
					fuelTypeId: undefined,
					gearboxTypeId: undefined,
				},
			},
			form2: {
				vehicle: {
					kilometersTraveled: undefined,
					childSeatNumber: undefined,
					hasAndroid: false,
				},
				price: 10,
				priceListItemDTO: {
					id: undefined,
				},
				kilometersPerDayLimit: undefined,
				collisionDamageWaiver: false,
				location: undefined,
			},
			form3: {
				description: undefined,
			},
			pricelistForm: {
				dailyPrice: undefined,
				cdwPrice: undefined,
				debtPrice: undefined,
				creatorId: 2,
			},
			sending: false,
			active: "first",
			first: false,
			second: false,
			third: false,
			newPriceList: false,
		};
	},
	created() {
		this.$store.dispatch("getCatalog");
	},
	computed: mapGetters(["isLogged", "getBrands", "getModels", "getVehicleClasses", "getGearboxTypes", "getFuelTypes", "getPriceList"]),
	methods: {
		...mapActions(["addAdvertisement", "addPhotos", "getCatalog"]),

		setDone(id, index) {
			this[id] = true;

			if (index) {
				this.active = index;
			}
		},

		submitAd() {
			this.sending = true;
			if (!this.form2.kilometersPerDayLimit) {
				this.form2.kilometersPerDayLimit = -1;
			}

			var form = {
				vehicle: {
					modelId: this.form1.vehicle.modelId,
					vehicleClassId: this.form1.vehicle.vehicleClassId,
					fuelTypeId: this.form1.vehicle.fuelTypeId,
					gearboxTypeId: this.form1.vehicle.gearboxTypeId,
					kilometersTraveled: this.form2.vehicle.kilometersTraveled,
					childSeatNumber: this.form2.vehicle.childSeatNumber,
					hasAndroid: this.form2.vehicle.hasAndroid,
				},
				price: this.form2.price,
				kilometersPerDayLimit: this.form2.kilometersPerDayLimit,
				collisionDamageWaiver: this.form2.collisionDamageWaiver,
				location: this.form2.location,
				description: this.form3.description,
				priceListItemDTO: this.form2.priceListItemDTO,
			};

			this.$store
				.dispatch("addAdvertisement", form)
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
						.dispatch("addPhotos", payload)
						.then(() => {
							this.$router.push("/");
						})
						.catch((error) => console.log(error));
				})
				.catch((error) => console.log(error));
		},

		handleFileChange() {
			this.photos = this.$refs.file.$refs.inputFile.files;
		},

		validateAd1() {
			this.$v.brandId.$touch();
			this.$v.form1.$touch();

			if (!this.$v.form1.$invalid || !this.$v.brandId.$invalid) {
				this.setDone("first", "second");
			}
		},

		validateAd2() {
			this.$v.form2.$touch();

			if (!this.$v.form2.$invalid) {
				this.setDone("second", "third");
			}
		},

		validateAd3() {
			this.$v.form3.$touch();

			if (!this.$v.form3.$invalid) {
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
			this.$store
				.dispatch("newPriceListItem", this.pricelistForm)
				.then((data) => {
					this.pricelistForm.dailyPrice = undefined;
					this.pricelistForm.cdwPrice = undefined;
					this.pricelistForm.debtPrice = undefined;
					this.newPriceList = false;
				})
				.catch((error) => console.log(error));
		},
	},
	validations: {
		brandId: {
			required,
		},
		form1: {
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
			},
		},
		form2: {
			vehicle: {
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
			price: {
				required,
				decimal,
			},
			priceListItemDTO: {
				id: {
					required,
				},
			},
			kilometersPerDayLimit: {
				integer,
			},
		},
		form3: {
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
.md-steppers {
	width: 70%;
	margin: 0 auto;
}

.somespan {
	width: 70%;
}

.outer {
	display: flex;
	align-items: baseline;
}

.full {
	flex: 1;
}
</style>
