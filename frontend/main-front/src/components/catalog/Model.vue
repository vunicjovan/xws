<template>
	<div class="md-layout md-alignment-top-center">
		<md-dialog :md-active.sync="active">
			<md-dialog-title>New Model</md-dialog-title>
			<md-dialog-content>
				<form>
					<md-field>
						<label for="brand">Brand</label>
						<md-select name="brand" v-model="form.brand.id">
							<md-option v-for="brand in getBrands" :key="brand.id" :value="brand.id">{{ brand.name }}</md-option>
						</md-select>
					</md-field>
					<md-field>
						<md-input v-model="form.name" placeholder="Model name..." />
					</md-field>
				</form>
			</md-dialog-content>
			<md-dialog-actions>
				<md-button class="md-primary" @click="active = false">Cancel</md-button>
				<md-button class="md-primary" @click.prevent="postModel()">Add</md-button>
			</md-dialog-actions>
		</md-dialog>

		<md-dialog-prompt
			:md-active.sync="editMode"
			v-model="form.name"
			md-title="Edit model"
			md-input-placeholder="Model name..."
			md-confirm-text="Update"
			@md-cancel="cancelUpdate"
			@md-confirm="updModel(form)"
		/>

		<md-table v-model="models" md-sort="brand.name" md-sort-order="asc" md-card class="md-layout-item md-size-80 md-small-size-150">
			<md-table-toolbar>
				<h1 class="md-title md-toolbar-section-start">Model</h1>
				<div class="md-toolbar-section-end">
					<md-button class="md-icon-button" @click="active = true"><md-icon class="fas fa-plus"/></md-button>
				</div>
			</md-table-toolbar>

			<md-table-row v-if="models.length !== 0" slot="md-table-row" slot-scope="{ item }">
				<md-table-cell md-label="ID" md-sort-by="id" md-numeric>{{ item.id }}</md-table-cell>
				<md-table-cell md-label="Brand" md-sort-by="brand.name">{{ item.brand.name }}</md-table-cell>
				<md-table-cell md-label="Name" md-sort-by="name">{{ item.name }}</md-table-cell>
				<md-table-cell md-label="Remove">
					<md-button @click="delModel(item.brand.id, item.id)" class="md-icon-button">
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
				brand: {
					id: undefined,
					name: undefined,
				},
			},
		};
	},
	computed: {
		...mapGetters(["getModels", "getBrands"]),
		models: {
			get() {
				return this.getModels;
			},
			set(models) {
				this.$store.commit("setModels", models);
			},
		},
	},
	methods: {
		...mapActions(["addModel", "updateModel", "deleteModel"]),
		delModel(brandId, modelId) {
			const payload = {
				brandId,
				modelId,
			};
			this.$store
				.dispatch("deleteModel", payload)
				.then(() => {})
				.catch((error) => console.lgo(error));
		},
		postModel() {
			const payload = {
				brandId: this.form.brand.id,
				model: this.form,
			};
			this.$store
				.dispatch("addModel", payload)
				.then((data) => {
					this.cancelUpdate();
				})
				.catch((error) => console.log(error));
			this.active = false;
		},
		prepareForUpdate(model) {
			this.form.id = model.id;
			this.form.name = model.name;
			this.form.brand.id = model.brand.id;
			this.editMode = true;
		},
		updModel(model) {
			const payload = {
				brandId: model.brand.id,
				model: model,
			};

			this.$store
				.dispatch("updateModel", payload)
				.then((data) => {
					this.cancelUpdate();
				})
				.catch((error) => console.log(error));
		},
		cancelUpdate() {
			this.form.id = undefined;
			this.form.name = undefined;
			this.form.brand.id = undefined;
			this.form.brand.id = undefined;
		},
	},
};
</script>

<style></style>
