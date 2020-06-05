<template>
  <div v-if="isLogged && advertisements">
      <md-table v-model="advertisements" md-sort="id" md-sort-order="asc" md-card class="md-layout-item md-size-80 md-small-size-150">
			<md-table-toolbar>
				<h1 class="md-title md-toolbar-section-start">Cart advertisements</h1>
			</md-table-toolbar>

			<md-table-row v-if="advertisements.length !== 0" slot="md-table-row" slot-scope="{ item }">
				<md-table-cell md-label="ID" md-sort-by="id" md-numeric>{{ item.id }}</md-table-cell>
				<md-table-cell md-label="Brand" md-sort-by="brand">{{ item.brand }}</md-table-cell>
                <md-table-cell md-label="Model" md-sort-by="model">{{ item.model }}</md-table-cell>
                <md-table-cell md-label="Location" md-sort-by="location">{{ item.location }}</md-table-cell>
				<md-table-cell md-label="Remove">
					<md-button @click="deleteCartItem(item.id)" class="md-icon-button">
						<md-icon class="fas fa-trash" />
					</md-button>
				</md-table-cell>
			</md-table-row>
		</md-table>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
    name: "Cart",

    computed: {
        ...mapGetters(["isLogged", "getUser", "getCartAdvertisements"]),
        advertisements: {
            get() {
                return this.getCartAdvertisements;
            },
            set(advertisements) {
                this.$store.commit("setCartAdvertisements", advertisements);
            }
        }
    },

    mounted() {
        this.$store
            .dispatch("getCart", this.getUser.id)
            .then((advertisementIdList) => {
                let parameters = new URLSearchParams();
                parameters.append('cart', advertisementIdList);
                this.$store
                    .dispatch("getCartItems", parameters);
            })
    },

    methods: {
        ...mapActions(["getCart", "getCartItems"]),

        deleteCartItem(advertisementId) {
            let payload = {
                cartId: this.getUser.id,
                cartItemId: advertisementId
            };

            this.$store.dispatch("deleteCartItem", payload);
        }
    }

}
</script>

<style>

</style>