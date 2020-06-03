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
					commit("setFuelTypes", catalog.fuelTypes), commit("setGearboxTypes", catalog.gearboxTypes), commit("setVehicleClasses", catalog.vehicleClasses);
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
				.then((gearboxType) => {
					commit("deleteGearboxType", id);
				})
				.catch((error) => reject(error));
		});
	},

	addGearboxType({ commit }, gearboxType) {
		return new Promise((resolve, reject) => {
			catalogApi
				.addGearboxType(gearboxType)
				.then((gearboxType) => {
					commit("addGearboxType", gearboxType);
				})
				.catch((error) => reject(error));
		});
	},

	updateGearboxType({ commit }, gearboxType) {
		return new Promise((resolve, reject) => {
			catalogApi
				.updateGearboxType(gearboxType)
				.then((gearboxType) => {
					commit("updateGearboxType", gearboxType);
				})
				.catch((error) => reject(error));
		});
	},

	// VEHICLE_CLASS ACTIONS
	deleteVehicleClass({ commit }, id) {
		return new Promise((resolve, reject) => {
			catalogApi
				.deleteVehicleClass(id)
				.then(() => {
					commit("deleteVehicleClass", id);
					resolve();
				})
				.catch((error) => reject(error));
		});
	},
	addVehicleClass({ commit }, vehicleClass) {
		return new Promise((resovle, reject) => {
			catalogApi
				.addVehicleClass(vehicleClass)
				.then((data) => {
					commit("addVehicleClass", data);
					resovle(data);
				})
				.catch((error) => reject(error));
		});
	},
	updateVehicleClass({ commit }, vehicleClass) {
		return new Promise((resolve, reject) => {
			catalogApi
				.updateVehicleClass(vehicleClass)
				.then((data) => {
					commit("updateVehicleClass", data);
					resolve(data);
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
	addGearboxType: (state, gearboxType) => state.gearboxTypes.push(gearboxType),
	updateGearboxType: (state, gearboxType) => {
		state.gearboxTypes.forEach((element) => {
			if (element.id == gearboxType.id) {
				element.name = gearboxType.name;
			}
		});
	},
	// VEHICLE_CLASS MUTATIONS
	deleteVehicleClass: (state, id) => {
		state.vehicleClasses = state.vehicleClasses.filter((vehicleClass) => vehicleClass.id != id);
	},
	addVehicleClass: (state, vehicleClass) => state.vehicleClasses.push(vehicleClass),
	updateVehicleClass: (state, vehicleClass) => {
		const index = state.vehicleClasses.findIndex((vClass) => vClass.id === vehicleClass.id);
		state.vehicleClasses.splice(index, 1, vehicleClass);
	},
};

export default {
	state,
	getters,
	actions,
	mutations,
};
