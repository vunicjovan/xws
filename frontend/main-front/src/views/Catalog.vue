<template>
	<div v-if="isLogged && getUser !== null && getUser.roles.includes('ADMIN')">
		<md-tabs md-alignment="centered">
			<md-tab id="tab-fuel-type" md-label="Fuel Types"><FuelType /></md-tab>
			<md-tab id="tab-gearbox-type" md-label="Gearbox Types"><GearboxType /></md-tab>
			<md-tab id="tab-brand" md-label="Brands"><Brand /></md-tab>
			<md-tab id="tab-model" md-label="Models"><Model /></md-tab>
			<md-tab id="tab-vehicle-class" md-label="Vehicle Classes"><VehicleClass /></md-tab>
		</md-tabs>
	</div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
	components: {
		FuelType: () => import("../components/catalog/FuelType.vue"),
		GearboxType: () => import("../components/catalog/GearboxType.vue"),
		Brand: () => import("../components/catalog/Brand.vue"),
		VehicleClass: () => import("../components/catalog/VehicleClass.vue"),
		Model: () => import("../components/catalog/Model.vue"),
	},
	data: function() {
		return {
		};
	},
	computed: {
		...mapGetters(["isLogged", "getUser"]),
	},
	created() {
		this.getCatalog();
	},
	methods: {
		...mapActions(["getCatalog"]),
	},
};
</script>

<style scoped>

</style>
