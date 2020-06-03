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
					commit("setFuelTypes", catalog.fuelTypes),
					commit("setGearboxTypes", catalog.gearboxTypes),
					commit("setBrands", catalog.brands)
				})
				.catch((error) => reject(error));
		});
	},

	// FUEL_TYPE ACTIONS
	deleteFuelType({ commit }, id) {
		return new Promise((resolve, reject) => {
			catalogApi
				.deleteFuelType(id)
				.then((fuelType) => {
					commit("deleteFuelType", id);
				})
				.catch((error) => reject(error));
		});
	},

	addFuelType({ commit }, fuelType) {
		return new Promise((resolve, reject) => {
			catalogApi
				.addFuelType(fuelType)
				.then((fuelType) => {
					commit("addFuelType", fuelType);
				})
				.catch((error) => reject(error));
		});
	},

	updateFuelType({ commit }, fuelType) {
		return new Promise((resolve, reject) => {
			catalogApi
				.updateFuelType(fuelType)
				.then((fuelType) => {
					commit("updateFuelType", fuelType);
				})
				.catch((error) => reject(error));
		});
	},

	// GEARBOX_TYPE ACTIONS
	deleteGearboxType({ commit }, id) {
		return new Promise((resolve, reject) => {
			catalogApi
				.deleteGearboxType(id)
				.then((gearboxType) => { commit("deleteGearboxType", id) })
				.catch((error) => reject(error));
		});
	},

	addGearboxType({ commit }, gearboxType) {
		return new Promise((resolve, reject) => {
			catalogApi
				.addGearboxType(gearboxType)
				.then((gearboxType) => { commit("addGearboxType", gearboxType) })
				.catch((error) => reject(error));
		});
	},

	updateGearboxType({ commit }, gearboxType) {
		return new Promise((resolve, reject) => {
			catalogApi
				.updateGearboxType(gearboxType)
				.then((gearboxType) => { commit("updateGearboxType", gearboxType) })
				.catch((error) => reject(error));
		});
	},

	//BRAND ACTIONS
	addBrand( { commit }, brand) {
		return new Promise((resolve, reject) => {
			catalogApi
				.addBrand(brand)
				.then((brand) => { commit("addBrand", brand) })
				.catch((error) => reject(error));
		})
	},

	updateBrand( { commit }, brand) {
		return new Promise((resolve, reject) => {
			catalogApi
				.updateBrand(brand)
				.then((brand) => { commit("updateBrand", brand) })
				.catch((error) => reject(error));
		})
	},

	deleteBrand( { commit }, id) {
		return new Promise((resolve, reject) => {
			catalogApi
				.deleteBrand(id)
				.then((brand) => { commit("deleteBrand", id) })
				.catch((error) => reject(error));
		})
	},

};

const mutations = {
	setFuelTypes: (state, types) => (state.fuelTypes = types),
	setGearboxTypes: (state, types) => (state.gearboxTypes = types),
	setModels: (state, models) => (state.models = models),
	setVehicleClasses: (state, classes) => (state.vehicleClasses = classes),
	// FUEL_TYPE MUTATIONS
	deleteFuelType: (state, id) => {
		state.fuelTypes = state.fuelTypes.filter((fuelType) => fuelType.id != id);
	},
	addFuelType: (state, fuelType) => state.fuelTypes.push(fuelType),
	updateFuelType: (state, fuelType) => {
		state.fuelTypes.forEach((element) => {
			if (element.id == fuelType.id) {
				element.name = fuelType.name;
			}
		});
	},
	// GEARBOX_TYPE MUTATIONS
	deleteGearboxType: (state, id) => {
		state.gearboxTypes = state.gearboxTypes.filter((gearboxType) => gearboxType.id != id);
	},
	addGearboxType: (state, gearboxType) => ( state.gearboxTypes.push(gearboxType) ),
	updateGearboxType: (state, gearboxType) => {
		state.gearboxTypes.forEach(element => {
			if (element.id == gearboxType.id) {
				element.name = gearboxType.name;
			}
		})
	},
	// BRAND MUTATIONS
	setBrands: (state, brands) => (state.brands = brands),
	addBrand: (state, brand) => (state.brands.push(brand)),
	updateBrand: (state, brand) => {
		state.brands.forEach(element => {
			if (element.id == brand.id) {
				element.name = brand.name;
			}
		})
	},
	deleteBrand: (state, id) => {
		state.brands = state.brands.filter((brand) => brand.id != id);
	},
};

export default {
	state,
	getters,
	actions,
	mutations,
};
