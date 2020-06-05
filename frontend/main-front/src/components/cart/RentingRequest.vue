<template>
	<div>
		<div v-if="!batch">
			<div v-for="(ad, index) in groups" :key="index">
				<md-card>
					<md-card-header>
						<h3>Request {{ index + 1 }}</h3>
					</md-card-header>
					<md-card-content> {{ ad.brand }} {{ ad.model }}, {{ ad.location }}, {{ ad.price }}€ </md-card-content>
				</md-card>
			</div>
		</div>
		<div v-else>
			<div v-for="(value, name, index) in groups" :key="index">
				<md-card>
					<md-card-header>
						<h3>Group {{ index + 1 }}</h3>
					</md-card-header>
					<md-card-content>
						<div v-for="ad in value" :key="ad.id">{{ ad.brand }} {{ ad.model }}, {{ ad.location }}, {{ ad.price }}€</div>
						<md-datepicker v-model="dates[index]">
							<label>Start date</label>
						</md-datepicker>
					</md-card-content>
				</md-card>
			</div>
		</div>
	</div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
	data() {
		return {
			batch: false,
			dates: [],
		};
	},
	computed: {
		...mapGetters(["getCartAdvertisements"]),
		groups: {
			get() {
				if (this.batch) {
					return groupBy(this.getCartAdvertisements, "ownerId");
				} else {
					return this.getCartAdvertisements;
				}
			},
			set(advertisements) {
				this.$store.commit("setCartAdvertisements", advertisements);
			},
		},
	},
	methods: {
		updateData(value, e) {
			console.log(value);
			console.log(e);
		},
	},
};

function groupBy(array, key) {
	const result = {};
	array.forEach((item) => {
		if (!result[item[key]]) {
			result[item[key]] = [];
		}
		result[item[key]].push(item);
	});
	return result;
}
</script>

<style scoped>
.md-card {
	margin-top: 1.5%;
	margin-bottom: 1.5%;
}
</style>
