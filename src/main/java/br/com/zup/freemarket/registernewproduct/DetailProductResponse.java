package br.com.zup.freemarket.registernewproduct;

import br.com.zup.freemarket.giveopinion.Opinion;
import br.com.zup.freemarket.uploadimage.ProductImage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DetailProductResponse {

    private String productName;
    private BigDecimal productValue;
    private String description;
    private Double averageProductRating;
    private Integer totalOpinions;
    private Set<String> links = new HashSet<>();
    private Set<ProductCharacteristicResponse> characteristics = new HashSet<>();
    private List<OpinionResponse>  opinions = new ArrayList<>();
    private List<QuestionResponse>  questions = new ArrayList<>();


    public DetailProductResponse(Product product) {
        this.productName = product.getName();
        this.productValue = product.getValue();
        this.description = product.getDescription();
        this.averageProductRating = calculateAverage(product.getOpinion());
        this.totalOpinions = product.getOpinion().size();
        this.links.addAll(product.getImages().stream().map(ProductImage::getUrl).collect(Collectors.toSet()));
        this.characteristics.addAll(product.getCharacteristics().stream().map(ProductCharacteristicResponse::new).collect(Collectors.toSet()));
        this.opinions.addAll(product.getOpinion().stream().map(OpinionResponse::new).collect(Collectors.toList()));
        this.questions.addAll(product.getQuestions().stream().map(QuestionResponse::new).collect(Collectors.toList()));
    }


    public String getProductName() {
        return productName;
    }

    public BigDecimal getProductValue() {
        return productValue;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getLinks() {
        return links;
    }

    public List<OpinionResponse> getOpinions() {
        return opinions;
    }

    public Set<ProductCharacteristicResponse> getCharacteristics() {
        return characteristics;
    }

    public List<QuestionResponse> getQuestions() {
        return questions;
    }

    public Double getAverageProductRating() {
        return averageProductRating;
    }

    public Integer getTotalOpinions() {
        return totalOpinions;
    }

    private Double calculateAverage(List<Opinion> opinions) {
        return opinions.stream().mapToDouble(Opinion::getRating).sum() / opinions.size();
    }
}
