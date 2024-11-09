package vn.iostar.SanBong.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.iostar.SanBong.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

}
