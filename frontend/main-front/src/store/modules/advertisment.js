import advertismentApi from "@/api/Advertisment.js";

const state = {
	advertisments: [],
};

const getters = {};

const actions = {
	addAdvertisment({ commit }, advertisment) {
		return new Promise((resolve, reject) => {
			advertismentApi
				.postAdvertisment(advertisment)
				.then((ad) => {
					resolve();
				})
				.catch((error) => reject(error));
		});
	},
};

const mutations = {};

export default {
	state,
	getters,
	actions,
	mutations,
};
