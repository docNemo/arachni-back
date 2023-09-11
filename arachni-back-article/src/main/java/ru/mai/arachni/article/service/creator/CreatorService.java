package ru.mai.arachni.article.service.creator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.mai.arachni.article.dto.request.creator.CreatorListRequest;
import ru.mai.arachni.article.dto.response.creator.CreatorListResponse;
import ru.mai.arachni.article.dto.response.creator.CreatorResponse;
import ru.mai.arachni.core.domain.Creator;
import ru.mai.arachni.core.repository.CreatorRepository;
import ru.mai.arachni.core.repository.pagerequest.OffsetBasedPageRequest;

import java.util.List;

@RequiredArgsConstructor
public class CreatorService {
    private final CreatorRepository creatorRepository;

    public CreatorListResponse getCreators(CreatorListRequest creatorListRequest) {
        Page<Creator> creatorPage = creatorRepository.findByCreatorContainingIgnoreCase(
                creatorListRequest.getSearchString(),
                new OffsetBasedPageRequest(
                        creatorListRequest.getSkip(),
                        creatorListRequest.getLimit(),
                        Sort.by(
                                creatorListRequest.getOrder(),
                                "creator"
                        )
                )
        );

        List<CreatorResponse> creatorResponses = creatorPage
                .getContent()
                .stream()
                .map(creator -> new CreatorResponse(creator.getIdCreator(), creator.getCreator()))
                .toList();

        return new CreatorListResponse(
                creatorResponses,
                creatorPage.getTotalElements()
        );
    }
}
