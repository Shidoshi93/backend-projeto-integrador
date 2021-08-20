package Api.repository;

import Api.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findByDonation(String donation_type);
    Post findByUserId(Integer user_id);
    Post findByUserType(String user_type);
    Post findByQty(Integer qtd);
    Post findByStatus(String status);
}



