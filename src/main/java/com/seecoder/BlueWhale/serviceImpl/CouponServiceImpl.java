package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.enums.CouponTypeEnum;
import com.seecoder.BlueWhale.enums.RoleEnum;
import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.Coupon;
import com.seecoder.BlueWhale.po.CouponGroup;
import com.seecoder.BlueWhale.po.User;
import com.seecoder.BlueWhale.repository.CouponGroupRepository;
import com.seecoder.BlueWhale.repository.CouponRepository;
import com.seecoder.BlueWhale.repository.StoreRepository;
import com.seecoder.BlueWhale.service.CouponService;
import com.seecoder.BlueWhale.util.SecurityUtil;
import com.seecoder.BlueWhale.vo.CouponGroupVO;
import com.seecoder.BlueWhale.vo.CouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponGroupRepository couponGroupRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    StoreRepository storeRepository;

    @Override
    public Boolean createCouponGroup(CouponGroupVO couponGroupVO) {
        if (couponGroupVO.getType()== CouponTypeEnum.FULL_REDUCTION){
            if (couponGroupVO.getSatisfaction()==null || couponGroupVO.getMinus()==null ||
            couponGroupVO.getSatisfaction()<couponGroupVO.getMinus()){
                throw BlueWhaleException.fullReductionCouponError();
            }
        }
        CouponGroup couponGroup=couponGroupVO.toPO();
        User user=securityUtil.getCurrentUser();
        couponGroup.setStoreId(user.getRole()==RoleEnum.STAFF ? user.getStoreId() : 0);
        couponGroupRepository.save(couponGroup);
        return true;
    }

    @Override
    public List<CouponGroupVO> getCouponGroups() {
        User user=securityUtil.getCurrentUser();
        switch (user.getRole()){
            case CUSTOMER:
                List<CouponGroup> couponGroups=couponGroupRepository.findByRestGreaterThan(0);
                List<Coupon> coupons=couponRepository.findByUserId(user.getId());
                Set<Integer> received=coupons.stream()
                        .map(Coupon::getGroupId)
                        .collect(Collectors.toSet());
                return couponGroups.stream()
                        .filter(x->!received.contains(x.getId()))
                        .map(x->wrapWithStoreId(x.toVO()))
                        .collect(Collectors.toList());
            case STAFF:
                return couponGroupRepository.findByStoreId(user.getStoreId()).stream()
                        .map(x->wrapWithStoreId(x.toVO())).collect(Collectors.toList());
            case MANAGER:
            case CEO:
                return couponGroupRepository.findAll().stream()
                        .map(x->wrapWithStoreId(x.toVO())).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Boolean receiveCoupon(Integer couponGroupId) {
        CouponGroup couponGroup=couponGroupRepository.findById(couponGroupId).get();
        if (couponGroup.getRest()<=0){
            throw BlueWhaleException.couponGroupNotEnough();
        }
        
        User user=securityUtil.getCurrentUser();
        Coupon coupon = new Coupon();
        coupon.setGroupId(couponGroupId);
        coupon.setUserId(user.getId());
        coupon.setUsed(false);
        
        couponGroup.setRest(couponGroup.getRest()-1);
        couponGroupRepository.save(couponGroup);
        couponRepository.save(coupon);
        return true;
    }

    @Override
    public List<CouponVO> getCoupons() {
        User user=securityUtil.getCurrentUser();
        return couponRepository.findByUserId(user.getId()).stream()
                .map(x->wrapWithGroupId(x.toVO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CouponVO> getCouponsWithStoreId(Integer storeId) {
        User user=securityUtil.getCurrentUser();
        return couponRepository.findByUserIdAndUsed(user.getId(), false).stream()
                .filter(x->{
                    CouponGroup couponGroup=couponGroupRepository.findById(x.getGroupId()).get();
                    return couponGroup.getStoreId()==0 || couponGroup.getStoreId().equals(storeId);
                }).map(x->wrapWithGroupId(x.toVO()))
                .collect(Collectors.toList());
    }

    private CouponVO wrapWithGroupId(CouponVO couponVO){
        CouponGroup couponGroup=couponGroupRepository.findById(couponVO.getGroupId()).get();
        couponVO.setSatisfaction(couponGroup.getSatisfaction());
        couponVO.setType(couponGroup.getType());
        couponVO.setMinus(couponGroup.getMinus());
        couponVO.setStoreId(couponGroup.getStoreId());
        couponVO.setStoreName(couponVO.getStoreId()==0?"商场发放":
                storeRepository.findById(couponGroup.getStoreId()).get().getName());
        return couponVO;
    }

    private CouponGroupVO wrapWithStoreId(CouponGroupVO couponGroupVO){
        couponGroupVO.setStoreName(couponGroupVO.getStoreId()==0?"商场发放":
                storeRepository.findById(couponGroupVO.getStoreId()).get().getName());
        return couponGroupVO;
    }

}
