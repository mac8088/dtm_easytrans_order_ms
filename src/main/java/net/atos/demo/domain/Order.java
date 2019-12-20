package net.atos.demo.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Order Entity
 */
@ApiModel(description = "Order Entity")
@Entity
@Table(name = "biz_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "money", nullable = false)
    private Long money;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private LocalDate createTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long id) {
        this.orderId = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Order userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMoney() {
        return money;
    }

    public Order money(Long money) {
        this.money = money;
        return this;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public Order createTime(LocalDate createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return orderId != null && orderId.equals(((Order) o).orderId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Order{" +
            "orderId=" + getOrderId() +
            ", userId=" + getUserId() +
            ", money=" + getMoney() +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
