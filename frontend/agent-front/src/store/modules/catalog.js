import catalogApi from "@/api/Catalog.js";

const state = {
	fuelTypes: [],
	gearboxTypes: [],
	models: [],
	vehicleClasses: [],
	brands: [],
};

const getters = {
	getFuelTypes: (state) => state.fuelTypes,
	getGearboxTypes: (state) => state.gearboxTypes,
	getModels: (state) => state.models,
	getVehicleClasses: (state) => state.vehicleClasses,
	getBrands: (state) => state.brands,
};

const actions = {
	getCatalog({ commit }) {
		return new Promise((resolve, reject) => {
			catalogApi
				.getCatalog()
				.then((catalog) => {
					commit("setFuelTypes", catalog.fuelTypes);
					commit("setGearboxTypes", catalog.gearboxTypes);
					commit("setBrands", catalog.brands);
					commit("setModels", catalog.models);
					commit("setVehicleClasses", catalog.vehicleClasses);
				})
				.catch((error) => reject(error));
		});
	},
};

const mutations = {
	setFuelTypes: (state, types) => (state.fuelTypes = types),
	setGearboxTypes: (state, types) => (state.gearboxTypes = types),
	setModels: (state, models) => (state.models = models),
	setVehicleClasses: (state, classes) => (state.vehicleClasses = classes),
	setBrands: (state, brands) => (state.brands = brands),
};

export default {
	state,
	getters,
	actions,
	mutations,
};
