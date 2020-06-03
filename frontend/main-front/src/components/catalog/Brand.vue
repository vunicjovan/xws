<template>
    <div class="md-layout md-alignment-top-center">
        <md-dialog-prompt 
            :md-active.sync="active" 
            v-model="form.name" 
            md-title="New Brand"
            md-input-placeholder="Brand name..."
            md-confirm-text="Add"
            @md-confirm="addBrand(form)"/>
        
        <md-dialog-prompt 
            :md-active.sync="editMode" 
            v-model="form.name" 
            md-title="Edit Brand"
            md-input-placeholder="Brand name..."
            md-confirm-text="Update"
            @md-cancel="cancelUpdate()"
            @md-confirm="updateBrand(form)"/>

        <md-table v-model="brands" md-sort="name" md-sort-order="asc" md-card class="md-layout-item md-size-80 md-small-size-150">
            <md-table-toolbar>
                <h1 class="md-title md-toolbar-section-start">Brand</h1>
                <div class="md-toolbar-section-end">
                    <md-button class="md-icon-button" @click="active = true">
                        <md-icon class="fas fa-plus"/>
                    </md-button>
                </div>
            </md-table-toolbar>

            <md-table-row v-if="brands.length !== 0" slot="md-table-row" slot-scope="{ item }">
                <md-table-cell md-label="ID" md-sort-by="id" md-numeric>{{ item.id }}</md-table-cell>
                <md-table-cell md-label="Name" md-sort-by="name">{{ item.name }}</md-table-cell>
                <md-table-cell md-label="Remove">
                    <md-button @click="deleteBrand(item.id)" class="md-icon-button">
                        <md-icon class="fas fa-trash"/>
                    </md-button>
                </md-table-cell>
                <md-table-cell md-label="Edit">
                    <md-button @click="prepareForUpdate(item)" class="md-icon-button">
                        <md-icon class="fas fa-pen"/>
                    </md-button>
                </md-table-cell>
            </md-table-row>
        </md-table>
    </div>
</template>

<script>
import {mapGetters, mapAction, mapActions} from "vuex";
export default {
    data() {
        return {
            active: false,
            editMode: false,
            form: {
                id: undefined,
                name: undefined
            }
        }
    },
    computed: {
        ...mapGetters(["getBrands"]),
        brands: {
            get () {
                return this.getBrands;
            },
            set (brands) {
                this.$store.commit('setBrands', brands)
            }
        }
    },
    methods: {
        ...mapActions(["addBrand", "updateBrand", "deleteBrand"]),
        prepareForUpdate(brand) {
            this.form.id = brand.id;
            this.form.name = brand.name;
            this.editMode = true;
        },
        cancelUpdate() {
            this.form.id = undefined;
            this.form.name = undefined;
        },

        f(s) {
            alert(s);
        }
    } 
}
</script>

<style scoped>
    .md-dialog .md-dialog-container {
        transform:  none;
    }
</style>
