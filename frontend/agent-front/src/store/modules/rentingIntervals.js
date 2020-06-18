import advertismentApi from "@/api/Advertisement.js";

const state = {

};

const getters = {

};

const actions = {
    addRentingInterval({ commit }, rentingInterval) {
        return new Promise((resolve, reject) => {
            advertismentApi
				.addRentingInterval(rentingInterval)
				.then((rentingInterval) => {
					resolve(rentingInterval);
				})
				.catch((error) => reject(error));
        });
    },

    postComment({ commit }, comment) {
        return new Promise((resolve, reject) => {
            advertismentApi
				.postComment(comment)
				.then((rentingInterval) => {
					resolve(comment);
				})
				.catch((error) => reject(error));
        });
    },

};

const mutations = {

}

export default {
    state,
    getters,
    actions,
    mutations
}