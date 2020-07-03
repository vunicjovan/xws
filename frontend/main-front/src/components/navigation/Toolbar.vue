<template>
	<div>
		<md-toolbar class="md-default" md-elevation="1">
			<router-link to="/">
				<span class="md-layout" :class="`md-alignment-center-center`">
					<img class="md-layout-item" alt="Vue logo" src="../../assets/logo1.png" />
					<md-tooltip>RentaSoul Homepage</md-tooltip>
				</span>
			</router-link>
			<div style="flex: 1;"></div>
			<searchDialog />
			<md-button v-if="isLogged && getUser !== null && getUser.roles.includes('AGENT')" to="/post-ad">
				<i class="fas fa-ad fa-2x"></i>
				<md-tooltip>Post new advertisement</md-tooltip>
			</md-button>
			<md-button v-if="isLogged && getUser !== null && (getUser.roles.includes('AGENT') || getUser.roles.includes('SIMPLE_USER'))" to="/chat">
				<i class="far fa-comments fa-2x"></i>
				<md-tooltip>RentaSoul Chatbox</md-tooltip>
			</md-button>
			<md-button v-if="isLogged && getUser !== null && getUser.roles.includes('ADMIN')" to="/unpublishedComments">
				<i class="fas fa-comment-medical fa-2x"></i>
				<md-tooltip>Publish/Reject comments</md-tooltip>
			</md-button>
			<md-button v-if="isLogged && getUser !== null && getUser.roles.includes('ADMIN')" to="/catalog">
				<i class="fas fa-warehouse fa-2x"></i>
				<md-tooltip>Catalog</md-tooltip>
			</md-button>
			<md-button v-if="isLogged && getUser !== null && getUser.roles.includes('ADMIN')" to="/users">
				<i class="fas fa-address-book fa-2x"></i>
				<md-tooltip>Active users</md-tooltip>
			</md-button>
			<md-button v-if="isLogged && getUser !== null && getUser.roles.includes('ADMIN')" to="/registrationRequests">
				<i class="fas fa-user-plus fa-2x"></i>
				<md-tooltip>Agent registration requests</md-tooltip>
			</md-button>
			<md-button v-if="isLogged && getUser !== null && getUser.roles.includes('SIMPLE_USER')" @click.native="$router.push('/cart')">
				<i class="fas fa-shopping-cart fa-2x"></i>
				<md-tooltip>Your cart</md-tooltip>
			</md-button>
			<md-button
				v-if="isLogged && getUser !== null && (getUser.roles.includes('AGENT') || getUser.roles.includes('SIMPLE_USER'))"
				@click.native="$router.push('/ads/published')"
			>
				<i class="fas fa-list fa-2x"></i>
				<md-tooltip>Published Ads</md-tooltip>
			</md-button>
			<md-button v-if="isLogged && getUser !== null && getUser.roles.includes('SIMPLE_USER')" @click.native="$router.push('/ads/rented')">
				<i class="fas fa-ad fa-2x"></i>
				<md-tooltip>Rented Ads</md-tooltip>
			</md-button>
			<md-button v-if="isLogged && getUser !== null && getUser.roles.includes('AGENT')" @click.native="$router.push('/statistic/' + getUser.id)">
				<i class="fa fa-signal fa-2x"></i>
				<md-tooltip>Statistic report</md-tooltip>
			</md-button>
			<md-button v-if="isLogged && getUser !== null && getUser.roles.includes('AGENT')" @click.native="$router.push('/pricelist')">
				<i class="fa fa-book fa-2x"></i>
				<md-tooltip>pricelist</md-tooltip>
			</md-button>
			<div style="flex: 1;"></div>
			<md-menu>
				<md-button md-menu-trigger>
					<i class="fa fa-user-circle fa-2x" aria-hidden="true"></i>
					<md-tooltip>Account options</md-tooltip>
				</md-button>

				<md-menu-content>
					<md-menu-item v-if="!isLogged">
						<i class="fas fa-sign-in-alt fa-lg"></i>
						<md-button @click.native="$router.push('/login')">Sign in</md-button>
						<div style="flex: 1;"></div>
					</md-menu-item>

					<md-menu-item v-if="!isLogged">
						<i class="fa fa-user-plus fa-lg" aria-hidden="true"></i>
						<md-button @click.native="$router.push('/register')">Register</md-button>
						<div style="flex: 1;"></div>
					</md-menu-item>

					<md-menu-item v-if="isLogged">
						<i class="fas fa-user fa-lg" aria-hidden="true"></i>
						<md-button @click.native="$router.push('/profile')">Your profile</md-button>
						<div style="flex: 1;"></div>
					</md-menu-item>

					<md-menu-item v-if="isLogged && getUser !== null && (getUser.roles.includes('AGENT') || getUser.roles.includes('SIMPLE_USER'))">
						<i class="fas fa-handshake fa-lg" aria-hidden="true"></i>
						<md-button @click.native="$router.push('/income')">Income</md-button>
						<div style="flex: 1;"></div>
					</md-menu-item>

					<md-menu-item v-if="isLogged && getUser !== null && getUser.roles.includes('SIMPLE_USER')">
						<i class="fas fa-cash-register fa-lg" aria-hidden="true"></i>
						<md-button @click.native="$router.push('/debts')">Debts</md-button>
						<div style="flex: 1;"></div>
					</md-menu-item>

					<md-menu-item v-if="isLogged">
						<i class="fas fa-key fa-lg" aria-hidden="true"></i>
						<md-button @click.native="$router.push('/password')">Change password</md-button>
					</md-menu-item>

					<md-menu-item v-if="isLogged">
						<i class="fas fa-sign-out-alt fa-lg"></i>
						<md-button @click="logout()">Log out</md-button>
						<div style="flex: 1;"></div>
					</md-menu-item>
				</md-menu-content>
			</md-menu>
		</md-toolbar>
	</div>
</template>

<script>
//import { EventBus } from "../../event-bus.js";
import { mapGetters, mapActions } from "vuex";

export default {
	name: "Toolbar",
	computed: {
		...mapGetters(["isLogged", "getUser"]),
	},
	methods: {
		...mapActions(["logout"]),
		logout() {
			this.$store.dispatch("logout").then(() => this.$router.push("/login"));
		},
	},
	components: {
		searchDialog: () => import("../search/Search.vue"),
	},
};
</script>

<style scoped>
a {
	text-decoration: none !important;
}
</style>
