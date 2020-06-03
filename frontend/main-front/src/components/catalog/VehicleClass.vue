<template>
	<div class="md-layout md-alignment-top-center">
		<md-dialog-prompt
			:md-active.sync="active"
			v-model="form.name"
			md-title="New vehicle class"
			md-input-placeholder="Vehicle class name..."
			md-confirm-text="Add"
			@md-confirm="submitVehicleClass(form)"
		/>

		<md-dialog-prompt
			:md-active.sync="editMode"
			v-model="form.name"
			md-title="Edit vehicle class"
			md-input-placeholder="Vehicle class name..."
			md-confirm-text="Update"
			@md-cancel="cancelUpdate()"
			@md-confirm="updateVC(form)"
		/>

		<md-table v-model="vehicleClasses" md-sort="name" md-sort-order="asc" md-card class="md-layout-item md-size-80 md-small-size-150">
			<md-table-toolbar>
				<h1 class="md-title md-toolbar-section-start">Vehicle Class</h1>
				<div class="md-toolbar-section-end">
					<md-button class="md-icon-button" @click="active = true"><md-icon class="fas fa-plus"/></md-button>
				</div>
			</md-table-toolbar>

			<md-table-row v-if="vehicleClasses.length !== 0" slot="md-table-row" slot-scope="{ item }">
				<md-table-cell md-label="ID" md-sort-by="id" md-numeric>{{ item.id }}</md-table-cell>
				<md-table-cell md-label="Name" md-sort-by="name">{{ item.name }}</md-table-cell>
				<md-table-cell md-label="Remove">
					<md-button @click="deleteVC(item.id)" class="md-icon-button">
						<md-icon class="fas fa-trash" />
					</md-button>
				</md-table-cell>
				<md-table-cell md-label="Edit">
					<md-button @click="prepareForUpdate(item)" class="md-icon-button">
						<md-icon class="fas fa-pen" />
					</md-button>
				</md-table-cell>
			</md-table-row>
		</md-table>
	</div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
	data() {
		return {
			active: false,
			editMode: false,
			form: {
				id: undefined,
				name: undefined,
			},
		};
	},
	computed: {
		...mapGetters(["getVehicleClasses"]),
		vehicleClasses: {
			get() {
				return this.getVehicleClasses;
			},
			set(vehicleClasses) {
				this.$store.commit("setVehicleClasses", vehicleClasses);
			},
		},
	},
	methods: {
		...mapActions(["deleteVehicleClass", "addVehicleClass", "updateVehicleClass"]),
		prepareForUpdate(vehicleClass) {
			this.form.id = vehicleClass.id;
			this.form.name = vehicleClass.name;
			this.editMode = true;
		},
		cancelUpdate() {
			this.form.id = undefined;
			this.form.name = undefined;
		},
		deleteVC(id) {
			this.$store
				.dispatch("deleteVehicleClass", id)
				.then((data) => {})
				.catch((error) => console.log(error));
		},
		updateVC(vehicleClass) {
			this.$store
				.dispatch("updateVehicleClass", vehicleClass)
				.then((data) => {
					this.cancelUpdate();
				})
				.catch((error) => console.log(error));
		},
		submitVehicleClass(vehicleClass) {
			this.$store
				.dispatch("addVehicleClass", vehicleClass)
				.then((data) => {})
				.catch((error) => console.log(error));
		},
	},
};
</script>

<style></style>
