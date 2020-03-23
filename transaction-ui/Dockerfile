FROM node:latest AS build
WORKDIR /usr/src/app
COPY package.json ./
RUN npm install
COPY . .
RUN npm run build
### STAGE 2: Run ###
FROM nginx:latest
COPY --from=build /usr/src/app/dist/transaction-ui /usr/share/nginx/html
COPY --from=build /usr/src/app/nginx.conf /etc/nginx/
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
