<template>
	<transition name="fade">
		<div v-if="show && getAdvertisement !== null" class="md-layout md-alignment-center-center">
			<md-dialog :md-active.sync="active">
				<md-dialog-title>Unavailable term</md-dialog-title>
				<md-dialog-content>
					<md-datepicker :class="{ 'md-invalid': $v.form.startDate.$error }" v-model="form.startDate">
						<label>Select start date</label>
						<span class="md-error" v-if="!$v.form.startDate.required">Start date is required</span>
					</md-datepicker>
					<md-datepicker :class="{ 'md-invalid': $v.form.endDate.$error }" v-model="form.endDate">
						<label>Select end date</label>
						<span class="md-error" v-if="!$v.form.endDate.required">End date is required</span>
					</md-datepicker>
				</md-dialog-content>
				<md-dialog-actions>
					<md-button class="md-primary" @click="active = false">Cancel</md-button>
					<md-button class="md-primary" @click.prevent="validateDates">Add</md-button>
				</md-dialog-actions>
			</md-dialog>

			<md-card>
				<md-card-media>
					<hooper :centerMode="true" :itemsToShow="1" :infiniteScroll="true" :progress="true" :autoPlay="true" :playSpeed="2000">
						<slide v-for="photo in getAdvertisement.photos" :key="photo">
							<img class="images img1" :src="getPhotoURL(getAdvertisement.id, photo)" alt="Vehicle image" />
						</slide>
						<hooper-navigation slot="hooper-addons"></hooper-navigation>
						<hooper-pagination slot="hooper-addons"></hooper-pagination>
					</hooper>
				</md-card-media>

				<md-card-header>
					<div class="md-title">
						<b>{{ getAdvertisement.brand }} {{ getAdvertisement.model }}</b> ({{ getAdvertisement.vehicleClass }}) - Owned by
						<b>{{ getAdvertisement.owner }}</b>
					</div>
					<div class="md-subhead"><b>Location:</b> {{ getAdvertisement.location }}</div>
					<div class="md-subhead"><b>Price:</b> {{ getAdvertisement.price }} €</div>
					<div class="md-subhead"><b>Fuel:</b> {{ getAdvertisement.fuel }}</div>
					<div class="md-subhead"><b>Gearbox:</b> {{ getAdvertisement.gearbox }}</div>
				</md-card-header>

				<md-card-content>
					<div>
						<b>Advanced details:</b><br />
						<i>This vehicle has traveled {{ getAdvertisement.kmTraveled }} kilometers. </i>
						<i v-if="getAdvertisement.dailyLimit !== -1">It has a daily kilometer limit of {{ getAdvertisement.dailyLimit }} kilometers for crossing. </i>
						<i v-else>It does not have a daily kilometer limit. </i>
						<i>Number of children seats in this vehicle is {{ getAdvertisement.childSeatNumber }}. </i>
						<i v-if="getAdvertisement.android">It has android support.</i>
						<i v-else-if="!getAdvertisement.android">It doesn't have android support.</i>
						<i v-if="getAdvertisement.cdw"> This vehicle has Collision Damage Waiver policy.</i>
						<i v-else-if="!getAdvertisement.cdw">This vehicle doesn't have Collision Damage Waiver policy.</i>
					</div>
					<br />
					<div><b>Owner's description:</b><br />{{ getAdvertisement.description }}</div>
				</md-card-content>

				<md-card-actions>
					<md-button v-if="getUser" @click="active = true" class="md-raised md-accent">Edit availability</md-button>
					<md-button v-if="getUser" @click="addCartItem(getAdvertisement.id)" class="md-raised md-accent">Add to cart</md-button>
				</md-card-actions>
			</md-card>
		</div>
	</transition>
</template>

<script>
import { Hooper, Slide, Pagination as HooperPagination, Navigation as HooperNavigation } from "hooper";
import "hooper/dist/hooper.css";
import { validationMixin } from "vuelidate";
import { required } from "vuelidate/lib/validators";

import { mapGetters, mapAction, mapActions } from "vuex";
export default {
	name: "DetailedView",
	mixins: [validationMixin],
	components: {
		Hooper,
		Slide,
		HooperPagination,
		HooperNavigation,
	},
	data: function() {
		return {
			ad: null,
			show: false,
			active: false,
			form: {
				startDate: undefined,
				endDate: undefined,
			},
		};
	},
	mounted: function() {
		this.$store.dispatch("getDetailedAdvertisement", this.$route.params.id);
		this.show = !this.show;
	},
	computed: {
		...mapGetters(["getAdvertisement", "getUser"]),
	},
	methods: {
		...mapActions(["getDetailedAdvertisement", "addCartItem"]),
		getPhotoURL(advertisementId, photoName) {
			return `http://localhost:8089/agent/images/${advertisementId}/${photoName}/`;
		},

		addCartItem(advertisementId) {
			let payload = {
				cartId: this.getUser.id,
				cartItemId: advertisementId,
			};

			this.$store.dispatch("addCartItem", payload);
		},

		addUnabailableTerm() {
			alert("Beep bop bop");
		},

		validateDates() {
			this.$v.$touch();

			if (!this.$v.$invalid) {
				this.showSearchDialog = false;
				this.addUnabailableTerm();
			}
		},
	},

	validations: {
		form: {
			startDate: {
				required,
			},
			endDate: {
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

.img1 {
	border-radius: 15%;
}

.fade-enter-active,
.fade-leave-active {
	transition: opacity 2.5s;
}

.fade-enter,
.fade-leave-to {
	opacity: 0;
}

.md-dialog .md-dialog-container {
	transform: none;
}
</style>
