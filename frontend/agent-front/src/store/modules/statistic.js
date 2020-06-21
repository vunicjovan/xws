import statisticApi from "@/api/Statistic.js";

const state = {
    statistic: null,
};

const getters = {
    getStatistic: (state) => state.statistic,
};

const actions = {
    getAdvertisementStatistic({ commit }, adId) {
        return new Promise((resolve, reject) => {
            statisticApi
                .getAdStatistics(adId)
                .then((statistic) => {
                    commit("setStatistic", statistic);
                    resolve(statistic);
                })
                .catch((error) => reject(error));
        });
    },
};

const mutations = {
    setStatistic: (state, statistic) => (state.statistic = statistic),
};

export default {
    state,
    getters,
    actions,
    mutations,
};
