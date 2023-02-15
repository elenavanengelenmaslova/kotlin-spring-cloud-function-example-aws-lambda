# kotlin-spring-cloud-function-example-aws-lambda
Spring cloud function in Kotlin example for AWS Lambda. Includes examples of different set-ups:
* X86
* ARM64
* X86 + C1 (tiered compilation optimisation)
* ARM64 + C1 (tiered compilation optimisation)
* SnapStart 
* SnapStart + C1 (tiered compilation optimisation)
* SnapStart + Priming (priming with CRaC hooks)
* SnapStart + C1 + Priming (tiered compilation optimisation and priming with CRaC hooks)

## Infrastructure
Kotlin Lambda Example contains a numbers of CDK stacks with a HelloWorld Kotlin/JVM lambda, one CDK stack for each of the set-up examples.

## Build & Deployment from local machine
### Build kotlin app
```
./gradlew clean build
```
### Set up CDK deployment

Install CDK (if you have not already):
```
npm install -g aws-cdk
```

If you have not set up CDK in you AWS account yet, please run (replace variables in brackets with actual values):
```
cdk bootstrap aws://[aws_account_id]/[aws_region]
```

Now deploy all stacks:
```
cdk deploy -vv --require-approval never --all
```

## Build & Deployment to AWS account from GitHub
Set up the following secrets in your GitHub project:
```
AWS_ACCOUNT_ID
AWS_ACCESS_KEY
AWS_SECRET_KEY
```
Update AWS region in `workflow-build-deploy.yml` in `.github` folder of the project

## Test the application
1. create an item in Dynamo DB table `Products-Spring-Cloud-Function-Example` :
```json
{
 "id": "1",
 "name": "Product1",
 "price": 5.99
}
```
Go to Lambda in console, find lambda variant to be tested and test that lambda with input:
```json
{
  "id": "1"
}
```
You should see the product json printed in the execution log (Execution result in the console)