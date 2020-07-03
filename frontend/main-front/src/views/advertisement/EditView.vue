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
							<label for="kilometersPerDayLimit">Limit: {{ getAdvertisement.dailyLimit}}</label>

						</md-field>
						<md-field>
							<label for="location">Location: {{ getAdvertisement.location }}</label>
							
						</md-field>
						<md-field :class="{ 'md-invalid': $v.form.price.$error }">
							<label for="price">Price</label>
							<span class="md-prefix">€</span>
							<md-input v-model="adPrice" type="number" step="0.01"></md-input>
							<span class="md-error" v-if="!$v.form.price.required">Price is required</span>
							<span class="md-error" v-else-if="!$v.form.price.decimal">Price must be number</span>
						</md-field>
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
			},
			sending: false,
        };
    },

    mounted: function() {
        this.$store.dispatch("getDetailedAdvertisement", this.$route.params.id)
    },

    computed: {
        ...mapGetters(["getAdvertisement", "getUser", "isLogged"]),
        hasKmLimit: {
            get() {
                if (this.getAdvertisement.dailyLimit !== -1) {
                    return true;
                } else {
                    return false;
                }
            }
        },
        adPrice: {
            get() {
                return this.getAdvertisement.price;
            },
            set(p) {
                this.form.price = p;
            }
        },
        adDescription: {
            get() {
                return this.getAdvertisement.description;
            },
            set(d) {
                this.form.description = d;
            }
        }

    },

    methods: {
        ...mapActions(["getDetailedAdvertisement"]),
        getPhotoURL(advertisementId, photoName) {
			return `http://localhost:8089/agent/images/${advertisementId}/${photoName}/`;
        },
        updateAd(adId) {
            let ad = {
                "adId": adId,
                "data": {
                    "price": this.price,
                    "description": this.description
                    }
            }

            this.$store.dispatch("putAdvertisement", ad)
                .then((data) => {
                    this.$router.push("/" + this.getAdvertisement.id)
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
			this.$v.$touch();

			if (!this.$v.$invalid) {
				this.updateAd(this.getAdvertisement.id);
			}
		},
    },

    validations: {
		form: {
			price: {
				required,
				decimal,
			},
			description: {
				required,
				sqli,
			},
		},
	},
}
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

.img1 {
	border-radius: 15%;
}

</style>
