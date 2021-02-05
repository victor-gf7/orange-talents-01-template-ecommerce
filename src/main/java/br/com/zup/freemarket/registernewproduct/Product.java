package br.com.zup.freemarket.registernewproduct;

import br.com.zup.freemarket.askquestion.NewQuestionRequest;
import br.com.zup.freemarket.askquestion.Question;
import br.com.zup.freemarket.giveopinion.NewOpinionRequest;
import br.com.zup.freemarket.giveopinion.Opinion;
import br.com.zup.freemarket.registernewcategory.Category;
import br.com.zup.freemarket.registernewuser.Usuario;
import br.com.zup.freemarket.uploadimage.ProductImage;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Product {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull @Positive
    @Column(nullable = false)
    private BigDecimal value;

    @NotNull @Min(0)
    @Column(nullable = false)
    private Integer availableQuantity;

    @NotBlank @Size(max = 1000)
    @Column(nullable = false)
    private String description;

    @NotNull @Valid
    @ManyToOne
    private Usuario user;

    @NotNull @Valid
    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<ProductImage> images = new HashSet<>();

    private LocalDateTime createdIn = LocalDateTime.now();

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private Set<ProductCharacteristic> characteristics = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private List<Opinion> opinion;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private List<Question> questions = new ArrayList<>();

    @Deprecated
    public Product() {
    }


    public Product(@NotBlank String name, @NotNull @Positive BigDecimal value,
                   @NotNull @Positive Integer availableQuantity,
                   @NotBlank @Size(max = 1000) String description, @NotNull @Valid Usuario user,
                   @NotNull @Valid Category category, @Valid @Size(min = 3) Collection<CharacteristicRequest> characteristics) {
        this.name = name;
        this.value = value;
        this.availableQuantity = availableQuantity;
        this.description = description;
        this.user = user;
        this.category = category;
        this.characteristics.addAll(characteristics
                .stream()
                .map(characteristic -> characteristic.converterToModel(this))
                .collect(Collectors.toSet()));

        Assert.state(this.characteristics.size() >= 3, "Todo produto deve possuir no mínimo três características");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (user == null) {
            return other.user == null;
        } else return user.equals(other.user);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public Set<ProductImage> getImages() {
        return images;
    }

    public LocalDateTime getCreatedIn() {
        return createdIn;
    }

    public Set<ProductCharacteristic> getCharacteristics() {
        return characteristics;
    }

    public List<Opinion> getOpinion() {
        return opinion;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Usuario getUser() {
        return user;
    }
    

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", availableQuantity=" + availableQuantity +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", category=" + category +
                ", images=" + images +
                ", createdIn=" + createdIn +
                ", characteristics=" + characteristics +
                ", opinion=" + opinion +
                ", questions=" + questions +
                '}';
    }

    public void associateImage(Set<String> links) {
        Set<ProductImage> images = links.stream().map(link -> new ProductImage(link, this)).collect(Collectors.toSet());

        this.images.addAll(images);
    }

    public void associateOpinion(NewOpinionRequest request, Usuario user) {
        this.opinion.add(request.converterToModel(user, this));
    }

    public void associateQuestion(NewQuestionRequest request, Usuario loggedUser) {
        this.questions.add(request.converterToModel(loggedUser, this));
    }

    public boolean slaughterStock(@Positive Integer quantity) {
        Assert.isTrue(quantity > 0, "A quantidade deve ser positiva para abater no estoque");

        if(this.availableQuantity > 0 && this.availableQuantity >= quantity){
            this.availableQuantity-=quantity;
            return true;
        }

        return false;
    }
}
