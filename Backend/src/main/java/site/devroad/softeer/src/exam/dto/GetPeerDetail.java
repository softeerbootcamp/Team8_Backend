package site.devroad.softeer.src.exam.dto;

import site.devroad.softeer.src.exam.dto.domain.PeerDetail;

import java.util.List;

public class GetPeerDetail {
    Boolean success;
    List<PeerDetail> peerDetails;
    public GetPeerDetail(Boolean success, List<PeerDetail> peerDetails) {
        this.success = success;
        this.peerDetails = peerDetails;
    }
    public Boolean getSuccess() {
        return success;
    }
    public List<PeerDetail> getPeerDetails() {
        return peerDetails;
    }

}
