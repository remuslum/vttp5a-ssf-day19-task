package sg.nus.edu.iss.vttp5a_ssf_day19_task2.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.nus.edu.iss.vttp5a_ssf_day19_task2.model.LocalDateConverter;
import sg.nus.edu.iss.vttp5a_ssf_day19_task2.model.Product;
import sg.nus.edu.iss.vttp5a_ssf_day19_task2.repo.MapRepo;
import sg.nus.edu.iss.vttp5a_ssf_day19_task2.util.Constants;

@Service
public class ProductService {
    
    @Autowired
    MapRepo mapRepo;

    public List<Product> getProductListFromRedis(String redisKey){
        List<Product> products = new ArrayList<>();
        Map<String, String> productsRedis = mapRepo.getEntries(redisKey);

        for(Entry<String, String> entry:productsRedis.entrySet()){
            products.add(convertRedisStringToProduct(entry.getValue()));
        }
        return products;
    }

    public Product getProductFromRedis(String redisKey, String productID){
        return convertRedisStringToProduct(mapRepo.get(redisKey, productID));
    }

    private Product convertRedisStringToProduct(String redisString){
        LocalDateConverter localDateConverter = new LocalDateConverter();
        JsonReader jsonReader = Json.createReader(new StringReader(redisString));
        JsonObject jsonObject = jsonReader.readObject();

        return new Product(jsonObject.getInt("id"), jsonObject.getString("title"), jsonObject.getString("description"), 
        jsonObject.getInt("price"), jsonObject.getJsonNumber("discountPercentage").doubleValue(), 
        jsonObject.getJsonNumber("rating").doubleValue(), jsonObject.getInt("stock"), 
        jsonObject.getString("brand"), jsonObject.getString("category"), 
        localDateConverter.convert(jsonObject.getJsonNumber("dated").longValue()), jsonObject.getInt("buy"));
    }

    public void submitEntryToRedis(String redisKey, Product product){
        LocalDateConverter localDateConverter = new LocalDateConverter();

        JsonObject jsonObject = Json.createObjectBuilder().add("id", product.getId()).add("title", product.getTitle())
        .add("description", product.getDescription()).add("price", product.getPrice()).add("discountPercentage", product.getDiscountPercentage())
        .add("rating", product.getRating()).add("stock", product.getStock()).add("brand", product.getBrand())
        .add("category", product.getCategory()).add("dated", Long.parseLong(localDateConverter.convertStringDateToLong(product.getDated().toString())))
        .add("buy", product.getBuy()).build();

        mapRepo.create(Constants.REDISKEY, String.valueOf(product.getId()), jsonObject.toString());
    }
}
