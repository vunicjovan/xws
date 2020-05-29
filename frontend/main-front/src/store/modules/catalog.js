import catalogApi from "@/api/Catalog.js";

const state = {
	fuelTypes: [],
	gearboxTypes: [],
	models: [],
	vehicleClasses: [],
};

const getters = {
	getFuelTypes: (state) => state.fuelTypes,
	getGearboxTypes: (state) => state.gearboxTypes,
	getModels: (state) => state.models,
	getVehicleClasses: (state) => state.vehicleClasses,
};

const actions = {
	getCatalog({ commit }) {
		return new Promise((resolve, reject) => {
			catalogApi
				.getCatalog()
				.then((catalog) => {})
				.cathc((error) => reject(error));
		});
	},
};

const mutations = {
	setFuelTypes: (state, types) => (state.fuelTypes = types),
	setGearboxTypes: (state, types) => (state.gearboxTypes = types),
	setModels: (state, models) => (state.models = models),
	setVehicleClasses: (state, classes) => (state.vehicleClasses = classes),
};

export default {
	state,
	getters,
	actions,
	mutations,
};
