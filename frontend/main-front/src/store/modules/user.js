import accountApi from "@/api/Account";

const state = {
	logged: false,
	loggedUser: Object,
};

const getters = {
	isLogged: (state) => state.logged,
	getUser: (state) => state.loggedUser,
};

const actions = {
	register({ commit }, user) {
		return new Promise((resolve, reject) => {
			accountApi
				.register(user)
				.then(() => {
					resolve();
				})
				.catch((error) => {
					reject(error);
				});
		});
	},

	login({ commit }, user) {
		return new Promise((resolve, reject) => {
			accountApi
				.login(user)
				.then(() => {
					commit("setLogged", true);
					resolve();
				})
				.catch((error) => {
					reject(error);
				});
		});
	},

	logout({ commit }) {
		localStorage.removeItem("auth");
		commit("setLogged", false);
		commit("setLoggedUser", null);
	},

	setLogged({ commit }, condition) {
		commit("setLogged", condition);
	},

	logged({ commit }) {
		return new Promise((resolve, reject) => {
			accountApi
				.logged()
				.then((user) => {
					commit("setLoggedUser", user);
					resolve();
				})
				.catch((error) => reject(error));
		});
	},
};

const mutations = {
	setLogged: (state, condition) => (state.logged = condition),
	setLoggedUser: (state, user) => (state.loggedUser = user),
};

export default {
	state,
	getters,
	actions,
	mutations,
};
