<template>
	<transition>
		<div>
			<div class="md-headline">Published Advertisements</div>
			<div v-if="show && isLogged" class="md-layout md-gutter md-alignment-center">
				<div class="card-expansion">
					<md-card v-for="ad in getAdvertisements" v-bind:key="ad.id" class="md-layout-item md-size-90">
						<md-card-header>
							<md-card-header-text>
								<p class="md-title">{{ ad.brand }} {{ ad.model }}</p>
								<p class="md-subtitle">{{ ad.price }} €</p>
								<p class="md-subhead">{{ ad.location }}</p>
							</md-card-header-text>
						</md-card-header>

						<md-card-expand>
							<md-card-expand-trigger>
								<md-button>Requests</md-button>
							</md-card-expand-trigger>

							<md-card-expand-content>
								<md-card-content>
									<md-table class="md-layout-item md-size-85">
										<md-table-row>
											<md-table-head>Start Date</md-table-head>
											<md-table-head>End Date</md-table-head>
											<md-table-head>Manage</md-table-head>
										</md-table-row>
										<md-table-row v-for="request in extractRequestsForAd(ad.id)" v-bind:key="request.id" slot="md-table-row">
											<md-table-cell md-label="Start Date"
												>{{ new Date(request.startDate).getDate() }}-{{ new Date(request.startDate).getMonth() + 1 }}-{{
													new Date(request.startDate).getFullYear()
												}}
												{{ new Date(request.startDate).getHours() }}:{{ new Date(request.startDate).getMinutes() }}</md-table-cell
											>
											<md-table-cell md-label="End Date"
												>{{ new Date(request.endDate).getDate() }}-{{ new Date(request.endDate).getMonth() + 1 }}-{{ new Date(request.endDate).getFullYear() }}
												{{ new Date(request.endDate).getHours() }}:{{ new Date(request.endDate).getMinutes() }}</md-table-cell
											>
											<md-table-cell md-label="Manage">
												<md-button @click.native="$router.push('/ads/published/request/' + request.id)" class="md-icon-button">
													<md-icon class="fas fa-pen" />
												</md-button>
											</md-table-cell>
										</md-table-row>
									</md-table>
								</md-card-content>
							</md-card-expand-content>
						</md-card-expand>
					</md-card>
				</div>
			</div>
		</div>
	</transition>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
	name: "PublishedView",

	data: function() {
		return {
			show: false,
		};
	},

	mounted: function() {
		if (this.getUser) {
			this.$store.dispatch("getUserPublishedAdvertisements", this.getUser.id);
			this.$store.dispatch("getUserRentingRequests", this.getUser.id);
		}
		this.show = !this.show;
	},

	computed: {
		...mapGetters(["getUser", "isLogged", "getAdvertisements", "getRentingRequests"]), //"getAdvertisements", "getRentingRequests"
	},

	methods: {
		...mapActions(["getUserPublishedAdvertisements", "getUserRentingRequests"]),
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
		},
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
</style>
