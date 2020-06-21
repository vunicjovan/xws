<template>
	<div v-if="!isLogged">
		<form novalidate class="md-layout md-alignment-top-center" autocomplete="off" @submit.prevent="validateUser">
			<md-card class="md-layout-item md-size-30 md-small-size-100">
				<md-card-header>
					<div class="md-title">Register</div>
				</md-card-header>
				<md-card-content>
					<div class="md-layout-item md-small-size-100">
						<md-field :class="getValidationClass('firstName')">
							<label for="first-name">First name</label>
							<md-input type="text" name="first-name" id="first-name" v-model="form.firstName" :disabled="sending" />
							<span class="md-error" v-if="!$v.form.firstName.required">The first name is required</span>
							<span class="md-error" v-else-if="!$v.form.firstName.lrx">Name should contain only capital words</span>
							<span class="md-error" v-else-if="!$v.form.firstName.minlength">First name must have at least 3 characters</span>
						</md-field>
					</div>
					<div class="md-layout-item md-small-size-100">
						<md-field :class="getValidationClass('lastName')">
							<label for="last-name">Last name</label>
							<md-input type="text" name="last-name" id="last-name" v-model="form.lastName" :disabled="sending" />
							<span class="md-error" v-if="!$v.form.lastName.required">The last name is required</span>
							<span class="md-error" v-else-if="!$v.form.lastName.lrx">Surname should contain only capital words</span>
							<span class="md-error" v-else-if="!$v.form.lastName.minlength">Last name must have at least 3 characters</span>
						</md-field>
					</div>
					<div class="md-layout-item md-small-size-100">
						<md-field :class="getValidationClass('email')">
							<label for="first-name">E-mail</label>
							<md-input type="email" name="email" id="email" v-model="form.email" :disabled="sending" />
							<span class="md-error" v-if="!$v.form.email.required">Email is required</span>
							<span class="md-error" v-else-if="!$v.form.email.email">Invalid email format</span>
						</md-field>
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
					<div class="md-layout-item md-small-size-100">
						<md-field :class="getValidationClass('repeatPassword')">
							<label for="first-name">Repeat Password</label>
							<md-input type="password" name="password" id="password-repeat" v-model="form.repeatPassword" :disabled="sending" />
							<span class="md-error" v-if="!$v.form.repeatPassword.required">Password is required</span>
							<span class="md-error" v-else-if="!$v.form.repeatPassword.sqli">At least 1: capital letter, digit and special character (#!?)</span>
							<span class="md-error" v-else-if="!$v.form.repeatPassword.minLength">Password requires at least 10 characters</span>
						</md-field>
					</div>
					<div class="md-layout-item md-small-size-100">
						<md-checkbox v-model="form.isAgent">Register as agent</md-checkbox>
					</div>
				</md-card-content>
				<md-card-actions>
					<md-button type="submit" class="md-primary" :disabled="sending">Register</md-button>
				</md-card-actions>
			</md-card>

			<md-snackbar :md-active.sync="userSaved">Registration request for user {{ lastUser }} was sent with success!</md-snackbar>
		</form>
	</div>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, minLength, email } from "vuelidate/lib/validators";
import { helpers } from "vuelidate/lib/validators";
import { mapActions } from "vuex";
import axios from "axios";
const sqli = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(?=.*[A-Z])(?=.*[0-9])(?=.*\W+)([a-zA-Z0-9!?#\s?]+)$/);
const lrx = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(([A-ZČĆŽŠĐ]){1,}[a-zčćšđžA-ZČĆŽŠĐ]+\s?)+$/);

export default {
	name: "Register",
	mixins: [validationMixin],
	data: function() {
		return {
			companies: null,
			form: {
				firstName: undefined,
				lastName: undefined,
				email: undefined,
				password: undefined,
				repeatPassword: undefined,
				isAgent: false,
			},
			userSaved: false,
			sending: false,
			lastUser: null,
		};
	},
	methods: {
		...mapActions(["register"]),
		register: function() {
			this.sending = true;

			window.setTimeout(() => {
				this.lastUser = `${this.form.firstName} ${this.form.lastName}`;
				this.userSaved = true;
				this.sending = false;
				this.clearForm();
			}, 1500);

			this.$store
				.dispatch("register", this.form)
				.then(() => this.$router.push("/login"))
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
				this.register();
			}
		},
		clearForm() {
			this.$v.$reset();
			this.form.firstName = undefined;
			this.form.lastName = undefined;
			this.form.email = undefined;
			this.form.password = undefined;
			this.form.repeatPassword = undefined;
			this.form.isAgent = false;
		},
	},
	validations: {
		form: {
			firstName: {
				required,
				lrx,
				minLength: minLength(3),
			},
			lastName: {
				required,
				lrx,
				minLength: minLength(3),
			},
			email: {
				required,
				email,
			},
			password: {
				required,
				sqli,
				minLength: minLength(10),
			},
			repeatPassword: {
				required,
				sqli,
				minLength: minLength(10),
			},
		},
	},
};
</script>

<style scoped>

</style>
