<template>
    <div>
        <transition name="fade">
            <md-tabs v-if="show" md-alignment="centered">
                <md-tab id="tab-fuel-type" md-label="Fuel Types"><FuelType/></md-tab>
                <md-tab id="tab-gearbox-type" md-label="Gearbox Types"><GearboxType/></md-tab>
                <md-tab id="tab-vehicle-class" md-label="Vehicle Classes"></md-tab>
            </md-tabs>
        </transition>
    </div>
</template>

<script>
import { mapActions } from "vuex"
export default {
    components: {
        FuelType: () => import("../components/catalog/FuelType.vue"),
        GearboxType: () => import("../components/catalog/GearboxType.vue")
    },
    data: function() {
		return {
			show: false
		};
	},
    created() {
        this.getCatalog();
    },
    mounted: function() {
		this.fadeMe();
	},
    methods: {
        ...mapActions(["getCatalog"]),
        fadeMe: function() {
			this.show = !this.show;
		},
    }

}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
	transition: opacity 2s;
}

.fade-enter,
.fade-leave-to {
	opacity: 0;
}
</style>