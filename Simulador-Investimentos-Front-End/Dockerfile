FROM node:20-alpine AS build
WORKDIR /app
RUN npm install -g @angular/cli@20.3.7
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build -- --output-path=./dist/frontend-simulador

FROM nginx:alpine AS production
RUN rm -rf /usr/share/nginx/html/*
COPY --from=build /app/dist/frontend-simulador /usr/share/nginx/html
RUN chmod -R 755 /usr/share/nginx/html
EXPOSE 83
CMD ["nginx", "-g", "daemon off;"]