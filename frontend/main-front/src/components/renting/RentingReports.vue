<template>
	<div class="md-layout md-gutter md-alignment-top-center">
		<md-card v-for="report in getReports" :key="report.id" class="md-layout-item md-size-50">
			<md-card-header>
				<md-card-header-text>
					<div>
						<p class="md-title">{{ report.vehicle }}</p>
						<p class="md-subtitle">Renting interval: {{ report.startDate }} - {{ report.endDate }}</p>
					</div>
				</md-card-header-text>
			</md-card-header>

			<md-card-content>
				<md-field>
					<label>Kilometers Traveled: {{ report.kilometers }}</label>
				</md-field>
				<md-field>
					<md-textarea readonly :value="report.content"> </md-textarea>
				</md-field>
			</md-card-content>
		</md-card>
	</div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
	name: "RentingReports",

	mounted() {
		if (this.getUser) this.$store.dispatch("getRentingReports", this.getUser.id);
	},

	computed: {
		...mapGetters(["getReports", "getUser"]),
	},

	watch: {
		getUser: {
			handler(val) {
				if (val) this.$store.dispatch("getRentingReports", val.id);
			},
		},
	},
};
</script>

<style></style>
