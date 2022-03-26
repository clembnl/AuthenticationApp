<template>
  <form @submit="onSubmit" class="signup-form">
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

    <input type="submit" value="Sign Up" class="btn" />
  </form>        
</template>

<script>
export default {
    name: "SignUpForm",
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

            this.$emit('signup', user)

            this.username = ""
            this.email = ""
            this.password = ""
        }
    }
}
</script>