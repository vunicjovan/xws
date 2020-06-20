import rentingHistoryApi from "@/api/RentingHistory.js";

const state = {
    rentingHistory: [],
};

const getters = {
    getRentingHistory: (state) => state.rentingHistory,
};

const actions = {
    getUserRentingHistory({ commit }, userId) {
        return new Promise((resolve, reject) => {
            rentingHistoryApi
                .getUserRentingHistory(userId)
                .then((rentingHistory) => {
                    commit("setRentingHistory", rentingHistory);
                    resolve();
                })
                .catch((error) => reject(error));
        });
    },
};

const mutations ={
    setRentingHistory: (state, rentingHistory) => (state.rentingHistory = rentingHistory),
}

export default {
    state, 
    getters,
    actions,
    mutations
};
