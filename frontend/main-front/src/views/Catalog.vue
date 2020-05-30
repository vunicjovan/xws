<template>
    <div>
        <md-tabs md-alignment="centered" class="ss">
            <md-tab id="tab-fuel-type" md-label="Fuel Types"><FuelType/></md-tab>
            <md-tab id="tab-vehicle-class" md-label="Vehicle Class"></md-tab>
        </md-tabs>
    </div>
</template>

<script>
import { mapActions } from "vuex"
export default {
    components: {
        FuelType: () => import("../components/catalog/FuelType.vue")
    },
    created() {
        this.getCatalog();
    },
    methods: {
        ...mapActions(["getCatalog"])
    }

}
</script>

<style scoped>
</style>