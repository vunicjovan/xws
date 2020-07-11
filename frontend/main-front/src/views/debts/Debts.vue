<template>
	<div class="somediv">
		<div class="md-headline">My debts</div>
		<flash-message class="myFlash"></flash-message>
		<md-list class="md-triple-line md-dense" v-for="debt in getDebts" v-bind:key="debt.id">
			<md-list-item>
				<md-avatar>
					<i class="fa fa-user-circle fa-2x" aria-hidden="true"></i>
				</md-avatar>

				<div class="md-list-item-text">
					<span class="ownerc">{{ debt.value }} €</span>
				</div>

				<span class="some-span">
					<md-button @click="payDebt(debt.id)" class="md-icon-button md-list-action">
						<i class="fas fa-check-circle fa-2x"></i>
						<md-tooltip>Pay</md-tooltip>
					</md-button>
				</span>
			</md-list-item>
			<md-divider class="md-inset"></md-divider>
		</md-list>
	</div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
	name: "Debts",
	data: function() {
		return {};
	},
	mounted() {
		if (this.getUser) this.$store.dispatch("getDebt", this.getUser.id);
	},
	computed: {
		...mapGetters(["getDebts", "getUser"]),
	},
	methods: {
		payDebt(id) {
			this.$store
				.dispatch("payDebt", id)
				.then((data) => {})
				.catch((error) => this.flashWarning(error.message, {timeout: 2000}));
		},
	},
	watch: {
		getUser: {
			handler(value) {
				if (value) this.$store.dispatch("getDebt", this.getUser.id);
			},
		},
	},
};
</script>

<style></style>
