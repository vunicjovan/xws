<template>
	<transition>
		<div v-if="isLogged && rentingRequest" class="md-layout md-alignment-top-center">
			<md-card>
				<md-card-header>
					<md-card-header-text>
						<p class="md-title">Start Date</p>
						<p class="md-subtitle">
							{{ new Date(rentingRequest.startDate).getDate() }}-{{ new Date(rentingRequest.startDate).getMonth() + 1 }}-{{
								new Date(rentingRequest.startDate).getFullYear()
							}}
							{{ new Date(rentingRequest.startDate).getHours() }}:{{ new Date(rentingRequest.startDate).getMinutes() }}
						</p>
						<p class="md-title">End Date</p>
						<p class="md-subtitle">
							{{ new Date(rentingRequest.endDate).getDate() }}-{{ new Date(rentingRequest.endDate).getMonth() + 1 }}-{{
								new Date(rentingRequest.endDate).getFullYear()
							}}
							{{ new Date(rentingRequest.endDate).getHours() }}:{{ new Date(rentingRequest.endDate).getMinutes() }}
						</p>
					</md-card-header-text>
				</md-card-header>
				<md-card-content>
					<md-table class="md-layout-item md-alignment-center md-size-100">
						<md-table-row>
							<md-table-head>Brand</md-table-head>
							<md-table-head>Model</md-table-head>
							<md-table-head>Location</md-table-head>
							<md-table-head>Price</md-table-head>
						</md-table-row>
						<md-table-row v-for="ad in rentingRequest.advertisements" v-bind:key="ad.id">
							<md-table-cell md-label="Brand">{{ ad.brand }}</md-table-cell>
							<md-table-cell md-label="Model">{{ ad.model }}</md-table-cell>
							<md-table-cell md-label="Location">{{ ad.location }}</md-table-cell>
							<md-table-cell md-label="Price">{{ ad.price }}€</md-table-cell>
						</md-table-row>
					</md-table>
				</md-card-content>
				<md-card-actions>
					<md-button @click="acceptRequest(rentingRequest)" class="md-primary">Accept</md-button>
					<md-button @click="declineRequest(rentingRequest)" class="md-accent">Decline</md-button>
				</md-card-actions>
			</md-card>
		</div>
	</transition>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
	name: "RentingRequest",

	data: function() {
		return {};
	},

	computed: {
		...mapGetters(["isLogged", "getUser", "getRentingRequest"]),
		rentingRequest: {
			get() {
				return this.getRentingRequest;
			},
			set(rentingRequest) {
				this.$store.commit("setRentingRequest", rentingRequest);
			},
		},
	},

	mounted() {
		this.$store.dispatch("getUserRentingRequest", this.$route.params.id);
	},

	methods: {
		...mapActions(["getUserRentingRequest", "updateRentingRequestStatus"]),
		acceptRequest: function(request) {
			let ret = {
				id: request.id,
				status: 2,
			};

			this.$store
				.dispatch("updateRentingRequestStatus", ret)
				.then((data) => {
					this.$router.push("/ads/published");
				})
				.catch((error) => console.log(error));
		},
		declineRequest: function(request) {
			let ret = {
				id: request.id,
				status: 3,
			};

			this.$store
				.dispatch("updateRentingRequestStatus", ret)
				.then((data) => {
					this.$router.push("/ads/published");
				})
				.catch((error) => console.log(error));
		},
	},
};
</script>

<style>
.md-card {
	margin: 2.5%;
	display: inline-block;
	vertical-align: top;
}

.fade-enter-active,
.fade-leave-active {
	transition: opacity 1s;
}

.fade-enter,
.fate-leave-to {
	opacity: 0;
}
</style>
