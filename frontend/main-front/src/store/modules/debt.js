import debtApi from "@/api/Debt.js";

const state = {
	debts: [],
	incomes: [],
};

const getters = {
	getDebts: (state) => state.debts,
	getIncomes: (state) => state.incomes,
};

const actions = {
	getDebt({ commit }, userId) {
		return new Promise((resolve, reject) => {
			debtApi
				.getDebt(userId)
				.then((data) => {
					commit("setDebts", data);
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},

	getIncome({ commit }, agentId) {
		return new Promise((resolve, reject) => {
			debtApi
				.getIncome(agentId)
				.then((data) => {
					commit("setIncomes", data);
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},

	payDebt({ commit }, id) {
		return new Promise((resolve, reject) => {
			debtApi
				.payDebt(id)
				.then((data) => {
					commit("deleteDebt", data);
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},
};

const mutations = {
	setDebts: (state, debts) => (state.debts = debts),
	setIncomes: (state, incomes) => (state.incomes = incomes),
	deleteDebt: (state, debt) => {
		console.log(debt);
		const index = state.debts.findIndex((d) => d.id === debt.id);
		console.log(index);
		if (index != -1) {
			state.debts.splice(index, 1);
		}
	},
};

export default {
	state,
	getters,
	actions,
	mutations,
};
