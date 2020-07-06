<template>
	<div v-if="isLogged && getUser !== null && (getUser.roles.includes('SIMPLE_USER'))">
		<div class="md-headline">Pending Renting Requests</div>
		<div class="md-layout md-gutter md-alignment-center">
			<div class="card-expansion">
				<md-card v-for="request in getRentingRequests" v-bind:key="request.id" class="md-layout-item md-size-90">
					<md-card-header>
						<md-card-header-text>
                            <div>
                                <div id="left-div"> 
                                    <p class="md-title">
                                        From 
                                        {{ new Date(request.startDate).getDate() }}-{{ new Date(request.startDate).getMonth() + 1 }}-
                                        {{new Date(request.startDate).getFullYear()}} {{ new Date(request.startDate).getHours() }}:{{ new Date(request.startDate).getMinutes() }}
                                    </p>
                                    <p class="md-title">
                                        Until
                                        {{ new Date(request.endDate).getDate() }}-{{ new Date(request.endDate).getMonth() + 1 }}-{{ new Date(request.endDate).getFullYear() }}
                                        {{ new Date(request.endDate).getHours() }}:{{ new Date(request.endDate).getMinutes() }}
                                    </p>
                                </div>
                                <div id="right-div">
                                    <md-button @click.native="cancelRentingRequest(request.id)" class="md-icon-button">
                                        <md-icon class="fas fa-trash" />
                                    </md-button>
                                </div>
                            </div>
						</md-card-header-text>
					</md-card-header>

					<md-card-expand>
						<md-card-expand-trigger>
							<md-button>Advertisements</md-button>
						</md-card-expand-trigger>

						<md-card-expand-content>
							<md-card-content>
								<md-table class="md-layout-item md-size-85">
									<md-table-row>
										<md-table-head>Brand</md-table-head>
										<md-table-head>Model</md-table-head>
										<md-table-head>Location</md-table-head>
										<md-table-head>Price</md-table-head>
									</md-table-row>
									<md-table-row v-for="ad in request.advertisements" v-bind:key="ad.id" slot="md-table-row">
										<md-table-cell md-label="Brand">{{ ad.brand }}</md-table-cell>
										<md-table-cell md-label="Model">{{ ad.model }}</md-table-cell>
										<md-table-cell md-label="Location">{{ ad.location }}</md-table-cell>
										<md-table-cell md-label="Price">{{ ad.price }} €</md-table-cell>
									</md-table-row>
								</md-table>
							</md-card-content>
						</md-card-expand-content>
					</md-card-expand>
				</md-card>
			</div>
		</div>
	</div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
	name: "PendingRequests",

	data: function() {
		return {
			show: false,
		};
	},

	mounted: function() {
		if (this.getUser) {
            this.$store.dispatch
			this.$store.dispatch("getUserRentingRequests", this.getUser.id);
		}
		this.show = !this.show;
	},

	computed: {
		...mapGetters(["getUser", "isLogged", "getAdvertisements", "getRentingRequests"]),
	},

	methods: {
		...mapActions(["getUserRentingRequests", "removeAdvertisement", "cancelRentingRequest"]),
		extractRequestsForAd(adId) {
			const requests = this.getRentingRequests;
			let retList = [];
			for (let i = 0; i < requests.length; i++) {
				for (let j = 0; j < requests[i].advertisements.length; j++) {
					if (adId == requests[i].advertisements[j].id) {
						retList.push(requests[i]);
					}
				}
			}
			return retList;
		}
	},

	watch: {
		getUser: function(val) {
			if (val) {
				this.$store.dispatch("getUserPublishedAdvertisements", val.id);
				this.$store.dispatch("getUserRentingRequests", val.id);
			}
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

#left-div {
	width: 70%;
	float: left;
}

#right-div {
	width: 20%;
	float: right;
}

.btn {
	float: right;
}

</style>
