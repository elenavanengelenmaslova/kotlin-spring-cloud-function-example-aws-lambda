# kotlin-aws-lambda-cdk-github-actions-template
Template for Kotlin Lambda with Infra in Kotlin CDK and CI/CD with GitHub actions

## Infrastructure
Kotlin Lambda Example contains 1 CDK stacks with a HelloWorld Kotlin/JVM lambda on ARM64 architecture

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
