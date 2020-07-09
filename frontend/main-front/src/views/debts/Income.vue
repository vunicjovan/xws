<template>
	<div class="somediv">
		<div class="md-headline">Debts to be cashed</div>
		<md-list class="md-triple-line md-dense" v-for="debt in getIncomes" v-bind:key="debt.id">
			<md-list-item>
				<md-avatar>
					<i class="fa fa-user-circle fa-2x" aria-hidden="true"></i>
				</md-avatar>

				<div class="md-list-item-text">
					<span class="ownerc">{{ debt.value }} €</span>
				</div>
			</md-list-item>
			<md-divider class="md-inset"></md-divider>
		</md-list>
	</div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
	name: "Income",
	data: function() {
		return {
			debts: [
				{
					id: 1,
					value: 1500,
				},
				{
					id: 2,
					value: 2500,
				},
				{
					id: 3,
					value: 3500,
				},
			],
		};
	},
	mounted() {
		if (this.getUser) this.$store.dispatch("getIncome", this.getUser.id);
	},
	computed: {
		...mapGetters(["getIncomes", "getUser"]),
	},
	watch: {
		getUser: {
			handler(value) {
				if (value) this.$store.dispatch("getIncome", this.getUser.id);
			},
		},
	},
};
</script>

<style></style>
