<template>
  <form @submit="onSubmit" class="signin-form">
    <div class="form-control">
      <label>Username</label>
      <input type="text" v-model="username" name="username" placeholder="Username" />
    </div>
    <div class="form-control">
      <label>Email</label>
      <input type="email" v-model="email" name="email" placeholder="Email" />
    </div>
    <div class="form-control">
      <label>Password</label>
      <input type="password" v-model="password" name="password" placeholder="Password" />
    </div>      

    <input type="submit" value="Sign In" class="btn" />
  </form>        
</template>

<script>
export default {
    name: "SignInForm",
    data() {
        return {
            username: "",
            email: "",
            password: "",
        }
    },
    methods: {
        onSubmit(e) {
            e.preventDefault()

            const user = {
                username: this.username,
                email: this.email,
                password: this.password,
            }

            this.$emit('signin', user)

            this.username = ""
            this.email = ""
            this.password = ""
        }
    }
}
</script>

<style>
.form-control {
  margin: 20px 0;
}
.form-control label {
  display: none;
}
.form-control input {
  width: 45%;
  height: 30px;
  padding: 3px 7px;
  font-family: 'Montserrat', sans-serif;
  font-size: 1em;
  border: none;
  border-radius: 5px;
  color: white;
  background-color: rgb(110, 110, 110);
}

.form-control :hover {
  outline: 1px solid dodgerblue;
}

.form-control :focus {
  outline: 2.5px solid dodgerblue;
}

.form-control input::placeholder {
  color: rgb(180, 180, 180);
  font-weight: bold;
}

.btn {
  background-color: dodgerblue;
  border: none;
  /*
  width: 11%;
  height: 35px;
  */
  padding: 6px 12px 6px 12px;
  border-radius: 20px;
  font-family: 'Poppins', sans-serif;
  font-size: 1.1em;
  font-weight: bold;
  color: white;
}
</style>