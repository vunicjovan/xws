<template>
	<div v-if="!isLogged">
		<form novalidate class="md-layout md-alignment-top-center" autocomplete="off" @submit.prevent="validateUser">
			<md-card class="md-layout-item md-size-30 md-small-size-100">
				<md-card-header>
					<div class="md-title">Login</div>
				</md-card-header>
				<md-card-content>
					<div class="md-layout md-gutter">
						<div class="md-layout-item md-small-size-100">
							<md-field :class="getValidationClass('email')">
								<label for="first-name">E-mail</label>
								<md-input type="email" name="email" id="email" v-model="form.email" :disabled="sending" />
								<span class="md-error" v-if="!$v.form.email.required">Email is required</span>
								<span class="md-error" v-else-if="!$v.form.email.email">Invalid email format</span>
							</md-field>
						</div>
					</div>
					<div class="md-layout-item md-small-size-100">
						<md-field :class="getValidationClass('password')">
							<label for="first-name">Password</label>
							<md-input type="password" name="password" id="password" v-model="form.password" :disabled="sending" />
							<span class="md-error" v-if="!$v.form.password.required">Password is required</span>
							<span class="md-error" v-else-if="!$v.form.password.sqli">At least 1: capital letter, digit and special character (#!?)</span>
							<span class="md-error" v-else-if="!$v.form.password.minLength">Password requires at least 10 characters</span>
						</md-field>
					</div>
				</md-card-content>
				<md-card-actions>
					<md-button type="submit" class="md-primary">Login</md-button>
				</md-card-actions>
				<br>
				<md-button class="pasbut md-primary" @click.native="$router.push('/sendReset')">Forgot your password?</md-button>
			</md-card>
			<md-snackbar :md-active.sync="userLogged">Welcome, {{ lastUser }}!</md-snackbar>
		</form>
	</div>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, minLength, email } from "vuelidate/lib/validators";
import { helpers } from "vuelidate/lib/validators";
const sqli = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(?=.*[A-Z])(?=.*[0-9])(?=.*\W+)([a-zA-Z0-9!?#\s?]+)$/);

import { mapActions, mapGetters } from "vuex";

export default {
	name: "Login",
	mixins: [validationMixin],
	data: function() {
		return {
			form: {
				email: undefined,
				password: undefined,
			},
			userLogged: false,
			sending: false,
			lastUser: null,
		};
	},
	computed: mapGetters(["isLogged"]),
	methods: {
		...mapActions(["login", "logged"]),
		login: function() {
			this.sending = true;

			window.setTimeout(() => {
				this.lastUser = `${this.form.email}`;
				this.userLogged = true;
				this.sending = false;
			}, 1500);

			this.$store
				.dispatch("login", this.form)
				.then(() => {
					this.$store
						.dispatch("logged")
						.then(() => this.$router.push("/"))
						.catch((error) => console.log(error));
				})
				.catch((error) => console.log(error));
		},
		getValidationClass(fieldName) {
			const field = this.$v.form[fieldName];

			if (field) {
				return {
					"md-invalid": field.$invalid && field.$dirty,
				};
			}
		},
		validateUser() {
			this.$v.$touch();

			if (!this.$v.$invalid) {
				this.login();
			}
		},
	},
	validations: {
		form: {
			email: {
				required,
				email,
			},
			password: {
				required,
				sqli,
				minLength: minLength(10),
			},
		},
	},
};
</script>

<style scoped>
	.pasbut {
		text-align: center;
		margin-top: auto;
		margin-bottom: auto;
		margin-left: 35%;
		margin-right: 15%;
		font-size: 70%;
	}
</style>
