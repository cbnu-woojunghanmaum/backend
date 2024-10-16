./gradlew api-module:clean
./gradlew api-module:build -x test

./gradlew save-heart-rate-module:clean
./gradlew save-heart-rate-module:build -x test


docker build --platform linux/amd64 --build-arg STAGE=docker -t bsj1209/smart-worker:latest api-module/.
docker build --platform linux/amd64 --build-arg STAGE=docker -t bsj1209/save-heart-rate:latest save-heart-rate-module/.

#docker build --build-arg STAGE=docker -t bsj1209/smart-worker:latest api-module/.
#docker build --build-arg STAGE=docker -t bsj1209/save-heart-rate:latest save-heart-rate-module/.

#docker push bsj1209/smart-worker:latest
#docker push bsj1209/save-heart-rate:latest

#docker pull bsj1209/smart-worker:latest
#docker pull bsj1209/save-heart-rate:latest
#docker compose -f common.yml -f api-module.yml up -d
