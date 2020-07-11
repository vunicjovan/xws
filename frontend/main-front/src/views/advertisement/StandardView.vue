<template>
	<div>
		<div class="md-layout md-alignment-center">
			<div class="md-layout-item md-alignment-center md-size-20 md-small-size-50 marginTop">
				<md-field>
					<label for="sort">Sort by:</label>
					<md-select v-model="sort" name="sort" id="sort">
						<md-option value="price-asc">Price asc</md-option>
						<md-option value="price-desc">Price desc</md-option>
						<md-option value="rating-asc">Rating asc</md-option>
						<md-option value="rating-desc">Rating desc</md-option>
						<md-option value="traveled-asc">Kilometers traveled asc</md-option>
						<md-option value="traveled-desc">Kilometers traveled desc</md-option>
					</md-select>
				</md-field>
			</div>
		</div>
		<div class="md-layout md-gutter md-alignment-top-center">
			<md-card
				v-for="ad in getAdvertisements"
				v-bind:key="ad.id"
				class="md-layout-item my-card md-xlarge-size-25 md-large-size-30 md-medium-size-45 md-small-size-55 md-xsmall-size-95"
			>
				<md-card-header>
					<md-card-header-text>
						<div>
							<p class="md-title">{{ ad.brand }} {{ ad.model }}</p>
							<p class="md-subtitle">{{ ad.price }} €</p>
							<p class="md-subhead">{{ ad.location }}</p>
							<p class="md-subhead">Rating: {{ ad.rating }}</p>
						</div>
					</md-card-header-text>

					<md-card-media class="md-layout-item md-size-45">
						<img :src="getPhotoURL(ad.id, ad.photo[0])" alt="Vehicle image" />
					</md-card-media>
				</md-card-header>

				<md-card-actions>
					<md-button @click.native="$router.push('/single-ad/' + ad.id)">Details</md-button>
					<md-button
						v-if="isLogged && getUser !== null && getUser.roles.includes('SIMPLE_USER') && getUser.id != ad.ownerId"
						@click="addCartItem(ad.id)"
						class="md-raised md-accent"
						>Add to cart</md-button
					>
				</md-card-actions>
			</md-card>
		</div>
	</div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
	name: "StandardView",
	data: function() {
		return {
			sort: "price-asc",
		};
	},
	mounted: function() {
		this.$store.dispatch("getAllAdvertisements").then((response) => {
			this.$store.commit("sortByPriceAsc");
		});
	},
	computed: {
		...mapGetters(["getAdvertisements", "getUser", "isLogged"]),
	},
	methods: {
		...mapActions(["getAllAdvertisements"]),
		getPhotoURL(advertisementId, photoName) {
			return `http://localhost:8089/agent/images/${advertisementId}/${photoName}/`;
		},

		addCartItem(advertisementId) {
			let payload = {
				cartId: this.getUser.id,
				cartItemId: advertisementId,
			};

			this.$store.dispatch("addCartItem", payload);
		},
	},

	watch: {
		sort(sortBy) {
			if (sortBy === "price-asc") {
				this.$store.commit("sortByPriceAsc");
			} else if (sortBy === "price-desc") {
				this.$store.commit("sortByPriceDesc");
			} else if (sortBy === "rating-asc") {
				this.$store.commit("sortByRatingAsc");
			} else if (sortBy === "rating-desc") {
				this.$store.commit("sortByRatingDesc");
			} else if (sortBy === "traveled-asc") {
				this.$store.commit("sortByKilometersTraveledAsc");
			} else if (sortBy === "traveled-desc") {
				this.$store.commit("sortByKilometersTraveledDesc");
			}
		},
	},
};
</script>

<style>
.md-card {
	margin: 1.5% !important;
	display: inline-block;
	vertical-align: top;
}

p {
	word-break: keep-all !important;
}

.my-card {
	margin: 0 auto;
}

.marginTop {
	margin-top: 2%;
}
</style>
