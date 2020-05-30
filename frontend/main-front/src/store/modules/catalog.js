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
				.then((catalog) => { commit("setFuelTypes", catalog.fuelTypes) })
				.catch((error) => reject(error));
		});
	},

	deleteFuelType({ commit }, id) {
		return new Promise((resolve, reject) => {
			catalogApi
				.deleteFuelType(id)
				.then((fuelType) => { commit("deleteFuelType", id) })
				.catch((error) => reject(error));
		});
	},

	addFuelType({ commit }, fuelType) {
		return new Promise((resolve, reject) => {
			catalogApi
				.addFuelType(fuelType)
				.then((fuelType) => { commit("addFuelType", fuelType) })
				.catch((error) => reject(error));
		});
	},

	updateFuelType({ commit }, fuelType) {
		return new Promise((resolve, reject) => {
			catalogApi
				.updateFuelType(fuelType)
				.then((fuelType) => { commit("updateFuelType", fuelType) })
				.catch((error) => reject(error));
		});
	},

};

const mutations = {
	setFuelTypes: (state, types) => (state.fuelTypes = types),
	setGearboxTypes: (state, types) => (state.gearboxTypes = types),
	setModels: (state, models) => (state.models = models),
	setVehicleClasses: (state, classes) => (state.vehicleClasses = classes),
	deleteFuelType: (state, id) => {
		state.fuelTypes = state.fuelTypes.filter((fuelType) => fuelType.id != id);
	},
	addFuelType: (state, fuelType) => ( state.fuelTypes.push(fuelType) ),
	updateFuelType: (state, fuelType) => {
		state.fuelTypes.forEach(element => {
			if (element.id == fuelType.id) {
				element.name = fuelType.name;
			}
		})
	},
	setBrands: (state, brands) => (state.brands = brands),
};

export default {
	state,
	getters,
	actions,
	mutations,
};
