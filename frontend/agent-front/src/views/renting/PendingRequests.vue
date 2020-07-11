<template>
	<div>
		<div class="md-headline">Pending Renting Requests</div>
		<div class="md-layout md-gutter md-alignment-top-center">
			<div class="card-expansion md-layout-item md-size-100">
				<md-card v-for="request in getPendingRequests" v-bind:key="request.id" class="md-layout-item md-size-60">
					<md-card-header>
						<md-card-header-text>
							<div>
								<div id="left-div">
									<p class="md-title">
										From
										{{ request.startDate }}
									</p>
									<p class="md-title">
										Until
										{{ request.endDate }}
									</p>
								</div>
								<div id="right-div">
									<md-button @click="approveRentingRequest(request.id)" class="md-icon-button">
										<md-icon class="fas fa-check-circle" />
									</md-button>
									<md-button @click.native="cancelRentingRequest(request.id)" class="md-icon-button">
										<md-icon class="fas fa-times-circle" />
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
								<div v-for="(ad, index) in request.advertisements" :key="index">
									<p class="md-subtitle">Vehicle: {{ ad }}</p>
								</div>
							</md-card-content>
						</md-card-expand-content>
					</md-card-expand>
				</md-card>
			</div>
		</div>
	</div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
	name: "PendingRequests",

	data: function() {
		return {
			show: false,
		};
	},

	mounted: function() {
		this.$store.dispatch("getPendingRentingRequests");
		this.show = !this.show;
	},

	computed: {
		...mapGetters(["getPendingRequests"]),
	},

	methods: {
		approveRentingRequest(id) {
			const payload = {
				id: id,
				status: 2,
			};

			this.$store
				.dispatch("updateRentingRequest", payload)
				.then((data) => {
					this.$store.dispatch("getPendingRentingRequests");
				})
				.catch((error) => console.log(error));
		},

		cancelRentingRequest(id) {
			const payload = {
				id: id,
				status: 3,
			};

			this.$store
				.dispatch("updateRentingRequest", payload)
				.then((data) => {
					this.$store.dispatch("getPendingRentingRequests");
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
